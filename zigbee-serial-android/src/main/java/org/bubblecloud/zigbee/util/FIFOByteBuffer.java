package org.bubblecloud.zigbee.util;

/**
 * @author <a href="mailto:christopherhattonuk@gmail.com">Chris Hatton</a>
 */
public interface FIFOByteBuffer
{
    void   push(byte value);
    void   pushAll(byte[] values);
    byte   pop();
    byte[] popAll();
    int    size();
    void   clear();
}
