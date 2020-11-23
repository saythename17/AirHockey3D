package com.icandothisallday2021.airhockey3d;

import android.opengl.GLES20;

public class ColorShaderProgram extends ShaderProgram{
    //Uniform locations
    private final int uMatrixLocation;

    //Attribute locations
    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram() {
        super();

        //Retrieve uniform locations for the shader program.
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        //Retrieve attribute locations for the shader program.
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
    }

    public void setUniforms(float[] matrix){
        //Pass the matrix into the shader program.
        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,matrix,0);
    }

    public int getPositionAttributeLocation(){return aPositionLocation;}

    public int getColorAttributeLocation() {return aColorLocation;}

}
