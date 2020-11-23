package com.icandothisallday2021.airhockey3d;

import android.opengl.GLES20;

public class Table {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDES = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;
    private final VertexArray vertexArray;

    private static final float[] VERTEX_DATA = {
            //Order of coordinates : X, Y, S, T

            //Triangle Fan
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
    };

    public Table() {
        this.vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureProgram) {
        //Getting the location of each attribute from the shader program.
        vertexArray.setVertexAttributePointer(0,
                textureProgram.getPositionAttributeLocation(),//This will bind the position data to the shader attribute referenced by this method.
                POSITION_COMPONENT_COUNT, STRIDES);
        vertexArray.setVertexAttributePointer(POSITION_COMPONENT_COUNT,
                textureProgram.getTextureCoordinatesAttributeLocations(),//bind the texture coordinate data to the shader attribute referenced by this method.
                TEXTURE_COORDINATES_COMPONENT_COUNT,STRIDES);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }
}
