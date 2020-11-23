package com.icandothisallday2021.airhockey3d;

import android.opengl.GLES20;

import static com.icandothisallday2021.airhockey3d.Constants.BYTES_PER_FLOAT;

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private final VertexArray vertexArray;

    private static final float[] VERTEX_DATA = {
            //Order of coordinates :  X, Y, R, G, B
            0f, -0.4f, 0f, 0f, 1f,
            0f, 0.4f, 1f, 0f, 0f
    };

    public Mallet() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorProgram){
        vertexArray.setVertexAttributePointer(0, colorProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, STRIDE);
        vertexArray.setVertexAttributePointer(POSITION_COMPONENT_COUNT, colorProgram.getColorAttributeLocation(), COLOR_COMPONENT_COUNT, STRIDE);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 2);
    }
}
