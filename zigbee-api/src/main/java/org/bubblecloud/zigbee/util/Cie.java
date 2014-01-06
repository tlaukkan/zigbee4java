package org.bubblecloud.zigbee.util;

/**
 * Cie color conversion.
 */
public class Cie {
    public double x;
    public double y;
    public double Y;

    public static Cie rgb2cie(double R, double G, double B) {
        double X = 0.4124f*R + 0.3576f*G + 0.1805f*B;
        double Y = 0.2126f*R + 0.7152f*G + 0.0722f*B;
        double Z = 0.0193f*R + 0.1192f*G + 0.9505f*B;

        Cie cie = new Cie();
        cie.x = X / (X + Y + Z);
        cie.y = Y / (X + Y + Z);
        cie.Y = (float) Math.sqrt(0.241f * R * R + 0.691f*G * G + 0.068f * B * B);
        return cie;
    }
}

