package com.icandothisallday2021.airhockey3d.Object;

import android.opengl.GLES20;

import com.icandothisallday2021.airhockey3d.Geometry;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;

public class ObjectBuilder {
    private static final int FLOATS_PER_VERTEX = 3;//how many floats we need for a vertex
    private final float[] vertexData;//hold these vertices
    private final List<DrawCommand> drawList = new ArrayList<DrawCommand>();
    interface DrawCommand { void draw(); }
    private int offset = 0;//track of the position in the array for the next vertex

    protected static class GeneratedData {
        final float[] vertexData;
        final List<DrawCommand> drawList;

        private GeneratedData(float[] vertexData, List<DrawCommand> drawList) {
            this.vertexData = vertexData;
            this.drawList = drawList;
        }
    }

    public ObjectBuilder(int sizeInVertices) {
        vertexData = new float[sizeInVertices * FLOATS_PER_VERTEX];
    }

    //calculate the size of a cylinder top in vertices
    private static int sizeOfCircleInVertices(int numPoints){
        return 1+ (numPoints + 1);
    }

    //calculate the size of a cylinder side in vertices
    private static int sizeOfOpenCylinderInVertices(int numPoints){
        return (numPoints + 1) * 2;
    }

    //Build a Puck with a Cylinder
    static GeneratedData createPuck(Geometry.Cylinder puck, int numPoints){
        int size = sizeOfCircleInVertices(numPoints) + sizeOfOpenCylinderInVertices(numPoints);
        ObjectBuilder builder = new ObjectBuilder(size);
        Geometry.Circle puckTop = new Geometry.Circle(puck.center.translateY(puck.height / 2f), puck.radius);
        builder.appendCircle(puckTop, numPoints);
        builder.appendOpenCylinder(puck, numPoints);

        return builder.build();
    }///////////////////////////////////////////createPuck()

    static GeneratedData createMallet(Geometry.Point center, float radius, float height, int numPoints){
        int size = sizeOfCircleInVertices(numPoints) * 2 + sizeOfOpenCylinderInVertices(numPoints) * 2;
        ObjectBuilder builder = new ObjectBuilder(size);

        //First, generate the mallet base.
        float baseHeight = height * 0.25f;

        Geometry.Circle baseCircle = new Geometry.Circle(center.translateY(-baseHeight),radius);
        Geometry.Cylinder baseCylinder = new Geometry.Cylinder(baseCircle.center.translateY(-baseHeight/2f), radius, baseHeight);

        builder.appendCircle(baseCircle, numPoints);
        builder.appendOpenCylinder(baseCylinder, numPoints);

        //New generate the mallet handle.
        float handleHeight = height * 0.75f;
        float handleRadius = radius / 3f;

        Geometry.Circle handleCircle = new Geometry.Circle(center.translateY(height * 0.5f),handleRadius);
        Geometry.Cylinder handleCylinder = new Geometry.Cylinder(handleCircle.center.translateY(-handleHeight / 2f),handleRadius,handleHeight);

        builder.appendCircle(handleCircle,numPoints);
        builder.appendOpenCylinder(handleCylinder, numPoints);

        return builder.build();
    }

    //Build a Circle with a Triangle Fan
    private void appendCircle(Geometry.Circle circle, int numPoints){
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfCircleInVertices(numPoints);
        //Center point of fan
        vertexData[offset++] = circle.center.x;
        vertexData[offset++] = circle.center.y;
        vertexData[offset++] = circle.center.z;

        //Fan around center point. <= is used because we want to generate the point at the starting angle twice to complete the fan.
        for(int i = 0; i <= numPoints; i++){
            float angleInRadians = ((float)i /(float)numPoints) * ((float)Math.PI * 2f);

            vertexData[offset++] = circle.center.x + circle.radius * ((float) cos(angleInRadians));//to find the x position of a point around the circle
            vertexData[offset++] = circle.center.y;//Since our circle is going to be lying flat on the x-z plane, the y component of the unit circle maps to our y position.
            vertexData[offset++] = circle.center.z + circle.radius * ((float) sin(angleInRadians));//to find the z position
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() { GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, startVertex, numVertices); }
        });
    }

    private void appendOpenCylinder(Geometry.Cylinder cylinder, int numPoints){
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfOpenCylinderInVertices(numPoints);
        final float yStart = cylinder.center.y - (cylinder.height / 2f);
        final float yEnd = cylinder.center.y + (cylinder.height / 2f);

        for(int i=0; i <= numPoints; i++){
            float angleInRadians = ((float)i / (float)numPoints) * ((float) Math.PI * 2f);
            float xPosition = cylinder.center.x + cylinder.radius * (float) cos(angleInRadians);
            float zPosition = cylinder.center.z + cylinder.radius * (float) sin(angleInRadians);

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPosition;

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yEnd;
            vertexData[offset++] = zPosition;
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, startVertex, numVertices);
            }
        });
    }

    private GeneratedData build(){
        return new GeneratedData(vertexData, drawList);
    }
}
