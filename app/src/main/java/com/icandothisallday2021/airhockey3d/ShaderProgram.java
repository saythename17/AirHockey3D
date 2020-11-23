package com.icandothisallday2021.airhockey3d;

import android.content.Context;
import android.opengl.GLES20;

public class ShaderProgram {
    //Uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    //Attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    //Shader program
    protected final int program;

    static String vertexShaderCode =   "uniform mat4 u_Matrix;" +//mat4 -> this uniform will represent a 4 x 4 matrix.
            "attribute vec4 a_Position;" +
            "attribute vec4 a_Color;" +
            "varying vec4 v_Color;" +
            "void main() {" +
            "    v_Color = a_Color;" +
            "    gl_Position = u_Matrix * a_Position;" +
            //our vertex array is interpreted as existing in a virtual coordinate space, as defined by the matrix.
            //The matrix will transform the coordinates from this virtual coordinate space back into normalized device coordinates.
            "gl_PointSize = 10.0; }";
    static String fragmentShaderCode = "precision mediump float; " +
            "varying vec4 v_Color;" +
            "void main() {" +
            "gl_FragColor = v_Color;" +
            "}";

    protected ShaderProgram() {
        //Compile the shaders and link the program.
        program = ShaderHelper.buildProgram(vertexShaderCode,fragmentShaderCode);
    }

    public void useProgram() {
        //Set the current OpenGL shader program to this program.
        GLES20.glUseProgram(program);
    }//OpenGL uses program for subsequent(차후의,그 다음에) rendering
}
