package org.bubblecloud.zigbee.util;

/**
 * @author <a href="mailto:christopherhattonuk@gmail.com">Chris Hatton</a>
 */
public class FIFOBuffers
{
    
    private FIFOBuffers() {}
    
    public static int popInto(FIFOByteBuffer source, byte[] destination)
    {
        int i=0;
        while(source.size()>0 && i<destination.length)
        {
            destination[i]=source.pop();
            ++i;
        }
        return i;
    }
}
