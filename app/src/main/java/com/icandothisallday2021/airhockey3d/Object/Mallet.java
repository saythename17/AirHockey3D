package com.icandothisallday2021.airhockey3d.Object;

import com.icandothisallday2021.airhockey3d.Geometry;
import com.icandothisallday2021.airhockey3d.Object.ObjectBuilder.*;
import com.icandothisallday2021.airhockey3d.Program.ColorShaderProgram;
import com.icandothisallday2021.airhockey3d.VertexArray;


import java.util.List;

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius, height;

    private final VertexArray vertexArray;
    private final List<DrawCommand> drawList;

    public Mallet(float radius, float height, int numPointsAroundMallet) {
        GeneratedData generatedData = ObjectBuilder.createMallet(new Geometry.Point(0f, 0f, 0f),radius,height,numPointsAroundMallet);

        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorProgram){
        vertexArray.setVertexAttributePointer(0,colorProgram.getPositionAttributeLocation(),POSITION_COMPONENT_COUNT,0);
    }

    public void draw(){
        for (DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
//    private static final int COLOR_COMPONENT_COUNT = 3;
//    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
//    private final VertexArray vertexArray;
//
//    private static final float[] VERTEX_DATA = {
//            //Order of coordinates :  X, Y, R, G, B
//            0f, -0.4f, 0f, 0f, 1f,
//            0f, 0.4f, 1f, 0f, 0f
//    };
//
//    public Mallet() {
//        vertexArray = new VertexArray(VERTEX_DATA);
//    }
//
//    public void bindData(ColorShaderProgram colorProgram){
//        vertexArray.setVertexAttributePointer(0, colorProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, STRIDE);
//        vertexArray.setVertexAttributePointer(POSITION_COMPONENT_COUNT, colorProgram.getColorAttributeLocation(), COLOR_COMPONENT_COUNT, STRIDE);
//    }
//
//    public void draw(){
//        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 2);
//    }
}
