
package org.bubblecloud.zigbee.network.port;

import android.hardware.usb.*;
import org.bubblecloud.zigbee.v3.SerialPort;
import org.bubblecloud.zigbee.util.ByteUtils;
import org.bubblecloud.zigbee.util.CircularFIFOByteBufferImpl;
import org.bubblecloud.zigbee.util.FIFOBuffers;
import org.bubblecloud.zigbee.util.FIFOByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Map;


/**
 * Android implementation of ZigBeePort
 * Intended for use with CC2531 dongle plugged into an Android USB port.
 * Requires a device with USB Host feature enabled (most do?).
 * Tested on a Samsung Galaxy S2 with OTG cable.
 * @author <a href="mailto:christopherhattonuk@gmail.com">Chris Hatton</a>
 */
public class AndroidUsbSerialPort implements SerialPort
{
    private static Logger logger = LoggerFactory.getLogger(AndroidUsbSerialPort.class);

    private static final int
        CC2531_USB_VENDOR_ID  = 0x0451, // Texas Instruments USB Vendor ID
        CC2531_USB_PRODUCT_ID = 0x16a8, // Product ID defined by the CC2531 firmware
        TIMEOUT_MS            = 5000;

    public enum Baud
    {
        Rate_38400  (38400),
        Rate_115200 (115200);

        Baud(int value) { this.value = value; }
        public final int value;

        static Baud valueOf(int baudRateValue)
        {
            for(Baud baud : Baud.values())
            {
                if(baud.value==baudRateValue)
                    return baud;
            }
            return null;
        }
    }

    public enum Stop
    {
        Bits_1   ((byte)0),
        Bits_1_5 ((byte)1),
        Bits_2   ((byte)2);

        Stop(byte lineCodingByte) { this.lineCodingByte = lineCodingByte; }
        public final byte lineCodingByte;
    }

    public enum Parity
    {
        None  ((byte)0),
        Odd   ((byte)1),
        Even  ((byte)2),
        Mark  ((byte)3),
        Space ((byte)4);

        Parity(byte lineCodingByte) { this.lineCodingByte = lineCodingByte; }
        public final byte lineCodingByte;
    }

    public enum DataBits
    {
        Bits_7 ((byte)7),
        Bits_8 ((byte)8);

        DataBits(byte lineCodingByte) { this.lineCodingByte = lineCodingByte; }
        public final byte lineCodingByte;
    }

    private final UsbManager manager;

    private final DataBits dataBits;
    private final Parity   parity;
    private final Stop     stop;
    private final Baud     baud;

    private ByteBuffer
        packetInBuffer,
        packetOutBuffer;

    private static final int BUFFER_SIZE=(4*1024);

    private final FIFOByteBuffer
        readBuffer  = new CircularFIFOByteBufferImpl(BUFFER_SIZE),
        writeBuffer = new CircularFIFOByteBufferImpl(BUFFER_SIZE);

    private UsbRequest
        readRequest,
        writeRequest;

    private final Object
        readMonitor  = new Object(),
        writeMonitor = new Object();

    private boolean
        isReading = false,
        isWriting = false;

    private UsbDeviceConnection deviceConnection;

    private InputStream  inputStream;
    private OutputStream outputStream;

    private UsbInterface dataInterface;

    public AndroidUsbSerialPort(UsbManager usbManager)
    {
        this(usbManager, Baud.Rate_38400, DataBits.Bits_8, Parity.None, Stop.Bits_1);
    }

    public AndroidUsbSerialPort(UsbManager manager, Baud baud, DataBits dataBits, Parity parity, Stop stop)
    {
        this.manager  = manager;
        this.dataBits = dataBits;
        this.parity   = parity;
        this.stop     = stop;
        this.baud     = baud;
    }

    private byte[] getLineCodingBytes(Baud baud, Stop stop, Parity parity, DataBits dataBits)
    {
        final int baudValue = baud.value;

        return new byte[]
        {
            (byte)( baudValue        & 0xFF),
            (byte)((baudValue >> 8 ) & 0xFF),
            (byte)((baudValue >> 16) & 0xFF),
            (byte)((baudValue >> 24) & 0xFF),

            stop    .lineCodingByte,
            parity  .lineCodingByte,
            dataBits.lineCodingByte
        };
    }

    @Override
    public boolean open()
    {
        try
        {
            if(baud==null)
                throw new IOException();

            UsbDevice cc2531Device = null;

            for (Map.Entry<String, UsbDevice> entry : manager.getDeviceList().entrySet())
            {
                UsbDevice device = entry.getValue();
                if (device.getVendorId() == CC2531_USB_VENDOR_ID && device.getProductId() == CC2531_USB_PRODUCT_ID)
                {
                    cc2531Device = device;
                    break;
                }
            }

            if(!manager.hasPermission(cc2531Device))
                manager.requestPermission(cc2531Device, null);

            if(cc2531Device==null)
                throw new IOException("Couldn't find CC2531 device.");

            if(cc2531Device.getInterfaceCount()<2)
                throw new IOException("Couldn't open CDC ACM device: interface count is too low");

            UsbInterface controlInterface = cc2531Device.getInterface(0);

            dataInterface = cc2531Device.getInterface(1);

            if(controlInterface.getInterfaceClass() != UsbConstants.USB_CLASS_COMM)
                throw new IOException("Couldn't open CDC ACM device: wrong ctrl interface class");

            if (dataInterface.getInterfaceClass() != UsbConstants.USB_CLASS_CDC_DATA)
                throw new IOException("Couldn't open CDC ACM device: wrong data interface class");

            final UsbEndpoint
                writeEndpoint = dataInterface.getEndpoint(1),
                readEndpoint  = dataInterface.getEndpoint(0);

            if(writeEndpoint.getDirection()!=UsbConstants.USB_DIR_OUT)
                throw new IOException("Couldn't open CDC ACM device: Wrong endpoint direction");

            if(readEndpoint.getDirection()!=UsbConstants.USB_DIR_IN)
                throw new IOException("Couldn't open CDC ACM device: Wrong endpoint direction");

            if(writeEndpoint.getType() != UsbConstants.USB_ENDPOINT_XFER_BULK)
                throw new IOException("Couldn't open CDC ACM device: Wrong endpoint type");

            if(readEndpoint.getType() != UsbConstants.USB_ENDPOINT_XFER_BULK)
                throw new IOException("Couldn't open CDC ACM device: Wrong endpoint type");

            packetOutBuffer = ByteBuffer.allocate(writeEndpoint.getMaxPacketSize());
            packetInBuffer  = ByteBuffer.allocate(readEndpoint.getMaxPacketSize());

            try
            {
                deviceConnection = manager.openDevice(cc2531Device);
            }
            catch(SecurityException e)
            {
                throw new IOException();
            }

            if (deviceConnection==null)
                throw new IOException("Couldn't open CDC ACM device");

            if (!deviceConnection.claimInterface(controlInterface, true))
                throw new IOException("Couldn't open CDC ACM device: failed to claim ctrl interface");

            if (!deviceConnection.claimInterface(dataInterface, true))
            {
                deviceConnection.releaseInterface(controlInterface);
                throw new IOException("Couldn't open CDC ACM device: failed to claim data interface");
            }

            sendLineCoding();

            inputStream = new InputStream()
            {
                @Override
                public int read() throws IOException
                {
                    synchronized (readMonitor)
                    {
                        if (readBuffer.size() == 0)
                            pullReadBuffer(0);

                        int value;

                        if (readBuffer.size() == 0)
                        {
                            value = -1;
                        }
                        else
                        {
                            //logger.debug("Returning single byte from buffer");

                            byte byteValue = readBuffer.pop();

							value = ByteUtils.intFromUnsignedByte(byteValue);
                        }

                        return value;
                    }
                }

                @Override
                public int read(byte[] readBufferOut) throws IOException
                {
                    synchronized (readMonitor)
                    {
                        int filled;

                        synchronized (readBuffer)
                        {
                            boolean isEnoughDataAlreadyBuffered = readBuffer.size()>=readBufferOut.length;

                            if (!isEnoughDataAlreadyBuffered)
                                pullReadBuffer(TIMEOUT_MS);

                            filled = FIFOBuffers.popInto(readBuffer, readBufferOut);

                            //logger.debug("Returning {} bytes from buffer",filled);
                        }

                        return filled;
                    }
                }

                private void pullReadBuffer(int timeOutMs) throws IOException
                {
                    int bytesRead;
                    boolean isCompletePacket;

                    synchronized (readMonitor)
                    {
                        while (isReading)
                        {
                            try
                            {
                                logger.debug("Waiting for current read pull to complete");
                                readMonitor.wait();
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }

                        do
                        {
                            if(readRequest!=null)
                                throw new IllegalStateException();

                            readRequest = new UsbRequest();
                            readRequest.initialize(deviceConnection, readEndpoint);
                            packetInBuffer.clear();


                            //logger.debug("Queueing read request");

                            isReading = true;
                            readRequest.queue(packetInBuffer, packetInBuffer.limit());

                            if (isReading) {
                                try {
                                    logger.debug(Thread.currentThread().getName() + ": Awaiting read request completion");

                                    while (isReading)
                                        readMonitor.wait(timeOutMs);

                                    readRequest = null;

                                    if (isReading) {
                                        isReading = false;
                                        throw new IOException("Read request timed out!");
                                    }
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            isCompletePacket = !packetInBuffer.hasRemaining();
                            bytesRead = packetInBuffer.position();

                            logger.debug("Read request completed with {} bytes", bytesRead);

                            synchronized (readBuffer) {
                                for (int i = 0; i < bytesRead; ++i)
                                    readBuffer.push(packetInBuffer.get(i));
                            }
                        }
                        while(isCompletePacket);
                    }
                }
            };

            outputStream = new OutputStream()
            {
                @Override
                public void write(int i) throws IOException
                {
                    if (deviceConnection == null)
                        throw new IOException("Connection closed.");

                    if (i < 0)
                        throw new IllegalArgumentException();

                    synchronized (writeMonitor)
                    {
						byte value = ByteUtils.unsignedByteFromInt(i);

                        writeBuffer.push(value);

                        pushWriteBuffer();
                    }
                }

                @Override
                public synchronized void write(byte[] writeBufferIn) throws IOException
                {
                    if (deviceConnection == null)
                        throw new IOException("Connection closed.");

                    if (writeBufferIn.length == 0)
                        return;

                    synchronized (writeMonitor)
                    {
                        for (byte value : writeBufferIn)
                            writeBuffer.push(value);

                        pushWriteBuffer();
                    }
                }

                private void pushWriteBuffer() throws IOException
                {
                    synchronized (writeMonitor)
                    {
                        while (isWriting)
                        {
                            try
                            {
                                logger.debug("Waiting for current write push to complete");
                                writeMonitor.wait();
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }

                        final int packetSize = packetOutBuffer.limit();

                        int bytesToWrite;

                        while ((bytesToWrite = writeBuffer.size()) > 0)
                        {
                            packetOutBuffer.clear();

                            int packetBytesToWrite = Math.min(bytesToWrite, packetSize);

                            for (int i = 0; i < packetBytesToWrite; ++i)
                                packetOutBuffer.put(writeBuffer.pop());

                            if(writeRequest!=null)
                                throw new IllegalStateException();

                            writeRequest = new UsbRequest();
                            writeRequest.initialize(deviceConnection, writeEndpoint);


                            logger.debug("Queueing write packet with {} bytes",packetOutBuffer.position());

                            isWriting = true;
                            writeRequest.queue(packetOutBuffer, bytesToWrite);

                            if (isWriting)
                            {
                                try
                                {
                                    logger.debug("Awaiting write request completion");

                                    writeMonitor.wait(TIMEOUT_MS);
                                }
                                catch (InterruptedException e)
                                {
                                    throw new RuntimeException(e);
                                }
                            }

                            writeRequest = null;

                            if (isWriting)
                                throw new IOException("Write request timed out!");

                            logger.debug("Write completed!");
                        }
                    }
                }
            };

            final Runnable notificationTask = new Runnable()
            {
                @Override
                public void run()
                {
                    UsbRequest notifiedRequest;

                    while(deviceConnection!=null)
                    {
                        logger.debug("Awaiting USB response...");

                        notifiedRequest = deviceConnection.requestWait();

                        if(notifiedRequest==readRequest)
                        {
                            synchronized (readMonitor)
                            {
                                logger.debug("Notifying read request completion");

                                isReading = false;
                                readMonitor.notifyAll();
                            }
                        }
                        else if(notifiedRequest==writeRequest)
                        {
                            synchronized (writeMonitor)
                            {
                                logger.debug("Notifying write request completion");

                                isWriting = false;
                                writeMonitor.notifyAll();
                            }
                        }
                        else
                        {
                            logger.debug("Unknown request completed.");
                        }
                    }
                }
            };

            logger.debug("Starting USB response listener thread...");

            final Thread notificationThread = new Thread(notificationTask, "CC2531AndroidPort_Notification");
            notificationThread.start();

            return true;
        }
        catch(IOException ioe)
        {
            return false;
        }
    }

    @Override
    public OutputStream getOutputStream() { return outputStream; }

    @Override
    public InputStream getInputStream()   { return inputStream; }

    private static final int
            SET_LINE_CODING      = 0x20,
            USB_RECIP_INTERFACE  = 0x01,
            USB_ACM_REQUEST_TYPE = UsbConstants.USB_TYPE_CLASS | USB_RECIP_INTERFACE;

    private void sendLineCoding() throws IOException
    {

        byte[] lineCodingBytes = getLineCodingBytes(baud,stop,parity,dataBits);

        int bytesSent = sendACMControlMessage(SET_LINE_CODING, 0, lineCodingBytes);

        if(bytesSent!=lineCodingBytes.length)
            throw new IOException("Couldn't send control message!");
    }

    private int sendACMControlMessage(int request, int value, byte[] message)
    {
        return deviceConnection.controlTransfer(USB_ACM_REQUEST_TYPE, request, value, 0, message, message.length, TIMEOUT_MS);
    }

    public void close()
    {
        boolean isOpen = deviceConnection != null;

        if (isOpen)
        {
            deviceConnection.close();
            deviceConnection = null;

            packetInBuffer = null;
            packetOutBuffer = null;

            readBuffer.clear();
            writeBuffer.clear();

            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                inputStream = null;
            }

            try
            {
                outputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                outputStream = null;
            }
        }
    }
}
