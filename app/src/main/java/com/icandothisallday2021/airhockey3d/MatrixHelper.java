package com.icandothisallday2021.airhockey3d;

public class MatrixHelper {
    public static void perspectiveM (float[] m, float yFovInDegrees, float aspect, float n, float f) {
        final float angleInRadians = (float) (yFovInDegrees * Math.PI /180.0);
        final float a = (float) (1.0/Math.tan(angleInRadians / 2.0));

        //OpenGL stores matrix data in column-major order
        //first column : x
        m[0] = a / aspect;
        m[1] = 0f;
        m[2] = 0f;
        m[3] = 0f;

        //second column : y
        m[4] = 0f;
        m[5] = a;
        m[6] = 0f;
        m[7] = 0f;

        //third column : z
        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((f+n) / (f-n));
        m[11] = -1f;

        m[12] = 0f;
        m[13] = 0f;
        m[14] = -((2f * f * n) / (f-n));
        m[15] = 0f;
    }
}
