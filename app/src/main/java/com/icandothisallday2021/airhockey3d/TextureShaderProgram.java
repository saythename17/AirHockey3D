package com.icandothisallday2021.airhockey3d;

import android.content.Context;
import android.opengl.GLES20;

public class TextureShaderProgram extends ShaderProgram{

    //Uniform locations
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    //Attribute locations
    private final int aPositionLocation;
    private final int aTextureCoordinatesLocations;

    protected TextureShaderProgram() {
        super();

        //Retrieve(회수하다) uniform locations for the shader program.
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);

        //Retrieve attribute locations for the shader program.
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesLocations = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    //Passing the matrix and texture into their uniforms.
    public void setUniforms(float[] matrix, int textureId) {
        //Pass the matrix into the shader program.
        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,matrix,0);

        //⭐️When we draw using textures in OpenGL, we don't pass the texture directly into the shader.
        //Instead, we use a texture unit to hold the texture.
        //It uses texture units to represent the active textures currently being drawn.
        //We cam swap texture in&out of texture units if we need to switch textures, though this may slow down rendering if we do it too often.
        //We can also use several texture units to draw more than one texture at the same time.

        //Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        //Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        //Tell the texture uniform sampler to use this texture in the shader by telling it to read from texture unit 0.
        GLES20.glUniform1i(uTextureUnitLocation,0);//pass in the selected texture unit to u_TextureUnit in the fragment shader by calling this method.
    }

    //Get the attribute location to bind them to the correct vertex array data.
    public int getPositionAttributeLocation(){
        return aPositionLocation;
    }

    public int getTextureCoordinatesAttributeLocations(){
        return aTextureCoordinatesLocations;
    }
}
