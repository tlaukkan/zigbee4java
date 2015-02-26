package org.bubblecloud.zigbee.util;

import java.util.LinkedList;

/**
 * Implementation of FIFOByteBuffer.
 * Distinguishing features vs. CircularFIFOByteBufferImpl:
 * - Can expand to any size
 * - Less memory efficient by a factor of at least 3 (each byte resides in the node of a LinkedList)
 * @author <a href="mailto:christopherhattonuk@gmail.com">Chris Hatton</a>
 */
public class LinkedListFIFOByteBufferImpl implements FIFOByteBuffer
{
    private final Object mutex = new Object();

    private LinkedList<Byte> buffer = new LinkedList<Byte>();

    @Override
    public void push(byte value)
    {
        synchronized (mutex)
        {
            buffer.addFirst(value);
        }
    }

    @Override
    public void pushAll(byte[] values)
    {
        synchronized (mutex)
        {
            for (byte value : values)
                buffer.add(value);
        }
    }

    @Override
    public byte pop()
    {
        synchronized (mutex)
        {
            return buffer.removeLast();
        }
    }

    @Override
    public byte[] popAll()
    {
        synchronized (mutex)
        {
            byte[] all = new byte[buffer.size()];
            int i = 0;
            for (byte value : buffer)
                all[++i] = value;

            buffer.clear();
            return all;
        }
    }

    @Override
    public final int size()
    {
        synchronized (mutex)
        {
            return buffer.size();
        }
    }

    @Override
    public void clear()
    {
        synchronized (mutex)
        {
            buffer.clear();
        }
    }
}
