package org.bubblecloud.zigbee.util;

/**
 * Cie color conversion.
 */
public class Cie {
    public double x;
    public double y;
    public double Y;

    public static Cie rgb2cie(double R, double G, double B) {
        double X = 0.412315f*R + 0.357600f*G + 0.180500f*B;
        double Y = 0.212600f*R + 0.715200f*G + 0.072200f*B;
        double Z = 0.019327f*R + 0.119200f*G + 0.950633f*B;

        Cie cie = new Cie();
        cie.x = X / (X + Y + Z);
        cie.y = Y / (X + Y + Z);
        cie.Y = Y;
        return cie;
    }
}

