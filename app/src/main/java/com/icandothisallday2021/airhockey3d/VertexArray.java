package com.icandothisallday2021.airhockey3d;

import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VertexArray {
    private final FloatBuffer floatBuffer;//vertex array data in native code

    public VertexArray(float[] vertexData) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * Constants.BYTES_PER_FLOAT)
                      .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
    }

    //To associate an attribute in our shader with the data.
    public void setVertexAttributePointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        floatBuffer.position(dataOffset);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);

        floatBuffer.position(0);
    }
}
//TODO :Drawing Our Texture -137p
