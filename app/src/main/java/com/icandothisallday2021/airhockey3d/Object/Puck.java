package com.icandothisallday2021.airhockey3d.Object;

import android.graphics.Color;

import com.icandothisallday2021.airhockey3d.Geometry;
import com.icandothisallday2021.airhockey3d.Program.ColorShaderProgram;
import com.icandothisallday2021.airhockey3d.VertexArray;
import com.icandothisallday2021.airhockey3d.Object.ObjectBuilder.*;

import java.util.List;

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;
    public final float radius, height;
    private final VertexArray vertexArray;
    private final List<DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsAroundPuck){
        GeneratedData generatedData = ObjectBuilder.createMallet(new Geometry.Point(0f,0f,0f), radius, height, numPointsAroundPuck);
        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    //Bind the vertex data to the attributes defined by the shader program.
    public void bindData(ColorShaderProgram colorProgram){
        vertexArray.setVertexAttributePointer(0, colorProgram.getPositionAttributeLocation(),POSITION_COMPONENT_COUNT,0);
    }

    public void draw(){
        for (DrawCommand drawCommand : drawList) drawCommand.draw();
    }
}
