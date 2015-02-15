package org.bubblecloud.zigbee;

import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.ZigBeeApiConstants;
import org.bubblecloud.zigbee.api.ZigBeeDeviceException;
import org.bubblecloud.zigbee.api.cluster.Cluster;
import org.bubblecloud.zigbee.api.cluster.general.LevelControl;
import org.bubblecloud.zigbee.api.cluster.general.OnOff;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ReportListener;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Reporter;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.general.ColorControl;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.util.Cie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * ZigBee command line console is an example application demonstrating usage of ZigBee API.
 *
 * @author <a href="mailto:tommi.s.e.laukkanen@gmail.com">Tommi S.E. Laukkanen</a>
 */
public class ZigBeeConsole {
    /**
     * The main thread.
     */
    private static Thread mainThread = null;

    /**
     * The flag reflecting that shutdown is in process.
     */
    private static boolean shutdown = false;

    /**
     * Map of registered commands and their implementations.
     */
    private static Map<String, ConsoleCommand> commands = new HashMap<String, ConsoleCommand>();

    /**
     * Main method of this console application.
     *
     * @param args the console arguments
     */
    public static void main(final String[] args) {
        mainThread = Thread.currentThread();

        final String serialPortName;
        final int channel;
        final int pan;
        final boolean resetNetwork;
        try {
            serialPortName = args[0];
            channel = Integer.parseInt(args[1]);
            pan = Integer.parseInt(args[2]);
            resetNetwork = args[3].equals("true");
        } catch (final Throwable t) {
            System.out.println("Syntax: java -jar zigbee4java.jar SERIALPORT CHANNEL PAN RESET");
            return;
        }

        System.out.print("ZigBee API starting up...");
        final ZigBeeApi zigbeeApi = new ZigBeeApi(serialPortName, pan, channel, resetNetwork);
        if (!zigbeeApi.startup()) {
            write(". [FAIL]");
            return;
        } else {
            write(". [OK]");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                shutdown = true;
                try {
                    System.in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mainThread.interrupt();
                    mainThread.join();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }));

        System.out.print("Browsing network ...");
        while (!shutdown && !zigbeeApi.isInitialBrowsingComplete()) {
            System.out.print('.');
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println(". [OK]");
        System.out.println("Found " + zigbeeApi.getDevices().size() + " nodes.");

        System.out.println("ZigBee console ready.");

        String command;
        while (!shutdown && (command = read()) != null) {
            process(zigbeeApi, command);
        }

        zigbeeApi.shutdown();
    }

    private static void process(final ZigBeeApi zigbeeApi, final String command) {
        if (command.length() == 0) {
            return;
        }
        final String[] args = command.split(" ");
        try {
            if (commands.containsKey(args[0])) {
                process(zigbeeApi, args, args[0]);
                return;
            } else {
                for (final String key : commands.keySet()) {
                    if (key.charAt(0) == command.charAt(0)) {
                        process(zigbeeApi, args, key);
                        return;
                    }
                }
                write("Uknown command. Use 'help' command to list available commands.");
            }
        } catch (final Exception e) {
            write("Exception in command execution: ");
            e.printStackTrace();
        }
    }

    private static void process(ZigBeeApi zigbeeApi, String[] args, String key) {
        final ConsoleCommand consoleCommand = commands.get(key);
        if (!consoleCommand.process(zigbeeApi, args)) {
            write(consoleCommand.getSyntax());
        }
    }

    /**
     * Writes line to console.
     *
     * @param line the line
     */
    private static void write(final String line) {
        System.out.println(line);
    }

    /**
     * Reads line from console.
     *
     * @return line read from console or null if exception occurred.
     */
    private static String read() {
        System.out.print("> ");
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String s = bufferRead.readLine();
            return s;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets device from ZigBee API either with index or endpoint ID
     * @param zigbeeApi the zigbee API
     * @param deviceIdentifier the device identifier
     * @return
     */
    private static Device getDeviceByIndexOrEndpointId(ZigBeeApi zigbeeApi, String deviceIdentifier) {
        Device device;
        try {
            device = zigbeeApi.getDevices().get(Integer.parseInt(deviceIdentifier));
        } catch (final Exception e) {
            device = zigbeeApi.getDevice(deviceIdentifier);
        }
        return device;
    }

    static {
        commands.put("quit", new QuitCommand());
        commands.put("help", new HelpCommand());
        commands.put("list", new ListCommand());
        commands.put("desc", new DescribeCommand());
        commands.put("bind", new BindCommand());
        commands.put("unbind", new UnbindCommand());
        commands.put("on", new OnCommand());
        commands.put("off", new OffCommand());
        commands.put("color", new ColorCommand());
        commands.put("level", new LevelCommand());
        commands.put("subscribe", new SubscribeCommand());
        commands.put("unsubscribe", new UnsubscribeCommand());
        commands.put("read", new ReadCommand());
        commands.put("write", new WriteCommand());
    }

    private static interface ConsoleCommand {
        String getDescription();
        String getSyntax();
        boolean process(final ZigBeeApi zigbeeApi, final String[] args);
    }

    private static class QuitCommand implements ConsoleCommand {
        public String getDescription() {
            return "Quits console.";
        }

        public String getSyntax() {
            return "quit";
        }

        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            shutdown = true;
            return true;
        }
    }

    private static class HelpCommand implements ConsoleCommand {
        public String getDescription() {
            return "View command help.";
        }

        public String getSyntax() {
            return "help [command]";
        }

        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {

            if (args.length == 2) {
                if (commands.containsKey(args[1])) {
                    final ConsoleCommand command = commands.get(args[1]);
                    System.out.println(command.getDescription());
                    System.out.println("");
                    System.out.println("Syntax: " + command.getSyntax());
                } else {
                    return false;
                }
            } else if (args.length == 1) {
                final List<String> commandList = new ArrayList<String>(commands.keySet());
                Collections.sort(commandList);
                write("Commands:");
                for (final String command : commands.keySet()) {
                    write(command + " - " + commands.get(command).getDescription());
                }
            } else {
                return false;
            }

            return true;
        }
    }

    private static class ListCommand implements ConsoleCommand {
        public String getDescription() {
            return "Lists devices.";
        }

        public String getSyntax() {
            return "list";
        }

        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            final List<Device> devices = zigbeeApi.getDevices();
            for (int i = 0; i < devices.size(); i++) {
                final Device device = devices.get(i);
                System.out.println(i + ") " + device.getEndpointId() + " : " + device.getDeviceType());
            }
            return true;
        }
    }

    private static class DescribeCommand implements ConsoleCommand {
        public String getDescription() {
            return "Describes a device.";
        }

        public String getSyntax() {
            return "desc DEVICEID";
        }

        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 2) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);

            if (device == null) {
                return false;
            }

            write("Network Address  : " + device.getNetworkAddress());
            write("Extended Address : " + device.getIEEEAddress());
            write("Endpoint Address : " + device.getEndPointAddress());
            write("Device Type      : " + ZigBeeApiConstants.getDeviceName(device.getDeviceTypeId()));
            write("Device Category  : " + ZigBeeApiConstants.getCategoryDeviceName(device.getDeviceTypeId()));
            write("Device Version   : " + device.getDeviceVersion());
            write("Input Clusters   : ");
            for (int c : device.getInputClusters()) {
                final Cluster cluster = device.getCluster(c);
                write("                 : " + c + " " + ZigBeeApiConstants.getClusterName(c));
                if (cluster != null) {
                    for (int a = 0; a < cluster.getAttributes().length; a++) {
                        final Attribute attribute = cluster.getAttributes()[a];
                        write("                 :    " + a
                                + " "
                                + "r"
                                + (attribute.isWritable() ? "w" : "-")
                                + (attribute.isReportable() ? "s" : "-")
                                + " "
                                + attribute.getName()
                                + " "
                                + (attribute.getReporter() != null ? "(" +
                                Integer.toString(attribute.getReporter().getReportListenersCount()) + ")" : "")
                                + "  [" + attribute.getZigBeeType() + "]");
                    }
                }
            }
            write("Output Clusters  : ");
            for (int c : device.getOutputClusters()) {
                final Cluster cluster = device.getCluster(c);
                write("                 : " + c + " " + ZigBeeApiConstants.getClusterName(c));
            }

            return true;
        }
    }

    private static class BindCommand implements ConsoleCommand {
        public String getDescription() {
            return "Binds a device to another device.";
        }
        public String getSyntax() {
            return "bind [CLIENT] SERVER CLUSTERID";
        }
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 3 && args.length != 4) {
                return false;
            }

            if (args.length == 3) {
                Device server = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
                final int clusterId;
                try {
                    clusterId = Integer.parseInt(args[2]);
                } catch (final NumberFormatException e) {
                    return false;
                }
                try {
                    server.bindToLocal(clusterId);
                } catch (final ZigBeeNetworkManagerException e) {
                    e.printStackTrace();
                }
            } else {
                Device client = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
                Device server = getDeviceByIndexOrEndpointId(zigbeeApi, args[2]);
                final int clusterId;
                try {
                    clusterId = Integer.parseInt(args[3]);
                } catch (final NumberFormatException e) {
                    return false;
                }
                try {
                    client.bindTo(server, clusterId);
                } catch (final ZigBeeNetworkManagerException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
    }

    private static class UnbindCommand implements ConsoleCommand {
        public String getDescription() {
            return "Unbinds a device from another device.";
        }
        public String getSyntax() {
            return "unbind CLIENT SERVER CLUSTERID";
        }
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 3 && args.length != 4) {
                return false;
            }

            if (args.length == 3) {
                Device server = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
                final int clusterId;
                try {
                    clusterId = Integer.parseInt(args[2]);
                } catch (final NumberFormatException e) {
                    return false;
                }
                try {
                    server.unbindFromLocal(clusterId);
                } catch (final ZigBeeNetworkManagerException e) {
                    e.printStackTrace();
                }
            } else {
                Device client = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
                Device server = getDeviceByIndexOrEndpointId(zigbeeApi, args[2]);
                final int clusterId;
                try {
                    clusterId = Integer.parseInt(args[3]);
                } catch (final NumberFormatException e) {
                    return false;
                }
                try {
                    client.unbindFrom(server, clusterId);
                } catch (final ZigBeeNetworkManagerException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
    }

    private static class OnCommand implements ConsoleCommand {
        public String getDescription() {
            return "Switches device on.";
        }

        public String getSyntax() {
            return "on DEVICEID";
        }

        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 2) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final OnOff onOff = device.getCluster(OnOff.class);
            try {
                onOff.on();
            } catch (ZigBeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    private static class ColorCommand implements ConsoleCommand {
        public String getDescription() {
            return "Changes light color.";
        }

        public String getSyntax() {
            return "color DEVICEID RED GREEN BLUE";
        }

        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 5) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final ColorControl colorControl = device.getCluster(ColorControl.class);
            if (colorControl == null) {
                write("Device does not support color control.");
                return false;
            }
            // @param colorX x * 65536 where colorX can be in rance 0 to 65279
            // @param colorY y * 65536 where colorY can be in rance 0 to 65279

            float red;
            try {
                red = Float.parseFloat(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            float green;
            try {
                green = Float.parseFloat(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }
            float blue;
            try {
                blue = Float.parseFloat(args[4]);
            } catch (final NumberFormatException e) {
                return false;
            }

            try {
                /*
                // RED
                int x = (int) (0.648427f * 65536);
                int y = (int) (0.330856f * 65536);
                colorControl.moveToColor(x, y, 10);*/
                Cie cie = Cie.rgb2cie(red, green ,blue);
                int x = (int) (cie.x * 65536);
                int y = (int) (cie.y * 65536);
                if (x > 65279) {
                    x = 65279;
                }
                if (y > 65279) {
                    y = 65279;
                }
                colorControl.moveToColor(x, y, 10);
            } catch (ZigBeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    private static class LevelCommand implements ConsoleCommand {
        public String getDescription() {
            return "Changes device level for example lamp brightness.";
        }

        public String getSyntax() {
            return "color DEVICEID LEVEL";
        }

        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 3) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final ColorControl colorControl = device.getCluster(ColorControl.class);
            if (colorControl == null) {
                write("Device does not support color control.");
                return false;
            }

            float level;
            try {
                level = Float.parseFloat(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }

            try {
                int l = (int) (level * 254);
                if (l > 254) {
                    l = 254;
                }
                if (l < 0) {
                    l = 0;
                }

                final LevelControl levelControl = device.getCluster(LevelControl.class);
                levelControl.moveToLevel((short) l, 10);
            } catch (ZigBeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    private static class OffCommand implements ConsoleCommand {
        public String getDescription() {
            return "Switches device off.";
        }

        public String getSyntax() {
            return "off DEVICEID";
        }

        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 2) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                return false;
            }
            final OnOff onOff = device.getCluster(OnOff.class);
            try {
                onOff.off();
            } catch (ZigBeeDeviceException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    private static class SubscribeCommand implements ConsoleCommand {
        public String getDescription() {
            return "Subscribe for attribute reports.";
        }
        public String getSyntax() {
            return "subscribe [DEVICE] [CLUSTER] [ATTRIBUTE]";
        }
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 4) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeIndex;
            try {
                attributeIndex = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeIndex).getReporter();

            if (reporter == null) {
                write("Attribute does not provide reports.");
                return true;
            }

            reporter.addReportListener(reportListener);

            return true;
        }
    }

    private static class UnsubscribeCommand implements ConsoleCommand {
        public String getDescription() {
            return "Subscribe for attribute reports.";
        }
        public String getSyntax() {
            return "unsubscribe [DEVICE] [CLUSTER] [ATTRIBUTE]";
        }
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 4) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeIndex;
            try {
                attributeIndex = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Reporter reporter = device.getCluster(clusterId).getAttribute(attributeIndex).getReporter();

            if (reporter == null) {
                write("Attribute does not provide reports.");
            }

            reporter.removeReportListener(reportListener);

            return true;
        }
    }


    private static class ReadCommand implements ConsoleCommand {
        public String getDescription() {
            return "Read an attribute.";
        }
        public String getSyntax() {
            return "read [DEVICE] [CLUSTER] [ATTRIBUTE]";
        }
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 4) {
                return false;
            }

            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeIndex;
            try {
                attributeIndex = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                write("Device not found.");
                return false;
            }

            final Cluster cluster = device.getCluster(clusterId);
            if (cluster == null) {
                write("Cluster not found.");
                return false;
            }

            final Attribute attribute = cluster.getAttributes()[attributeIndex];
            if (attribute == null) {
                write("Attribute not found.");
                return false;
            }

            try {
                write(attribute.getName() + "=" + attribute.getValue());
            } catch (ZigBeeClusterException e) {
                write("Failed to read attribute.");
                e.printStackTrace();
            }

            return true;
        }
    }

    private static class WriteCommand implements ConsoleCommand {
        public String getDescription() {
            return "Write an attribute.";
        }
        public String getSyntax() {
            return "write [DEVICE] [CLUSTER] [ATTRIBUTE] [VALUE]";
        }
        public boolean process(final ZigBeeApi zigbeeApi, final String[] args) {
            if (args.length != 5) {
                return false;
            }

            final int clusterId;
            try {
                clusterId = Integer.parseInt(args[2]);
            } catch (final NumberFormatException e) {
                return false;
            }
            final int attributeIndex;
            try {
                attributeIndex = Integer.parseInt(args[3]);
            } catch (final NumberFormatException e) {
                return false;
            }

            final Device device = getDeviceByIndexOrEndpointId(zigbeeApi, args[1]);
            if (device == null) {
                write("Device not found.");
                return false;
            }

            final Cluster cluster = device.getCluster(clusterId);
            if (cluster == null) {
                write("Cluster not found.");
                return false;
            }

            final Attribute attribute = cluster.getAttributes()[attributeIndex];
            if (attribute == null) {
                write("Attribute not found.");
                return false;
            }
            
            if(attribute.isWritable() == false) {
            	write(attribute.getName() + " is not writable");
            	return true;
            }

            try {
            	Object val = null;
            	switch(attribute.getZigBeeType()) {
				case Bitmap16bit:
					break;
				case Bitmap24bit:
					break;
				case Bitmap32bit:
					break;
				case Bitmap8bit:
					break;
				case Boolean:
					break;
				case CharacterString:
					val = new String(args[4]);
					break;
				case Data16bit:
					break;
				case Data24bit:
					break;
				case Data32bit:
					break;
				case Data8bit:
					break;
				case DoublePrecision:
					break;
				case Enumeration16bit:
					break;
				case Enumeration8bit:
					break;
				case IEEEAddress:
					break;
				case LongCharacterString:
					break;
				case LongOctectString:
					break;
				case OctectString:
					break;
				case SemiPrecision:
					break;
				case SignedInteger16bit:
					break;
				case SignedInteger24bit:
					break;
				case SignedInteger32bit:
					break;
				case SignedInteger8bit:
					break;
				case SinglePrecision:
					break;
				case UnsignedInteger16bit:
					break;
				case UnsignedInteger24bit:
					break;
				case UnsignedInteger32bit:
					break;
				case UnsignedInteger8bit:
					break;
				default:
					break;
            	}
                attribute.setValue(val);
            } catch (ZigBeeClusterException e) {
                write("Failed to write attribute.");
                e.printStackTrace();
            }

            return true;
        }
    }

    private static ReportListener reportListener = new ReportListener() {
        @Override
        public void receivedReport(final Dictionary<Attribute, Object> reports) {
            final Enumeration<Attribute> attributes = reports.keys();
            while (attributes.hasMoreElements()) {
                final Attribute attribute = attributes.nextElement();
                final Object value = reports.get(attribute);
                write(attribute.getName() + "=" + value);
            }
        }
    };

}
