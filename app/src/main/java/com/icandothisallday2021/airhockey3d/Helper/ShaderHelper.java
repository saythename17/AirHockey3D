package com.icandothisallday2021.airhockey3d.Helper;

import android.opengl.GLES20;
import android.util.Log;

import com.icandothisallday2021.airhockey3d.LoggerConfig;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    //takes in source code for a shader and the shader's type
    //If OpenGL was able to successfully compile the shader, then return the shader objectID(otherwise 0).
    public static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);

        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) Log.w(TAG, "Could not create new shader.");

            return 0;
        }

        glShaderSource(shaderObjectId, shaderCode);//Pass in the shader source.
        glCompileShader(shaderObjectId);

        //check whether the compile(of shader) failed or succeeded
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        //^read the compile status associated with shaderObjectID and write it to the 0 element of compileStatus

        if (LoggerConfig.ON)
            //Print the shader info log to the Android log output.
            Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:" + glGetShaderInfoLog(shaderObjectId));

        //Verity the compile status
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId);//If it failed, delete the shader object.
            if (LoggerConfig.ON) Log.w(TAG, "Compilation of shader failed. Did you write the shaderCode(glsl) wrong?");

            return 0;
        }

        return shaderObjectId;
    }

    //Linking shaders together into an OpenGL program.(bind them into a single program)
    public static int linkProgram(int vertexShaderId, int fragmentShaderId){
        final int programObjectId = glCreateProgram();

        if(programObjectId == 0){//0 : if the object creation failed
            if(LoggerConfig.ON) Log.w(TAG,"Could not create new program");
            return 0;
        }

        //attach the each shader to the program object
        glAttachShader(programObjectId,vertexShaderId);
        glAttachShader(programObjectId,fragmentShaderId);

        glLinkProgram(programObjectId);


        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId,GL_LINK_STATUS,linkStatus,0);
        if(LoggerConfig.ON){
            //Print the program info log to the Logcat
            Log.v(TAG,"Results of linking program:\n"+ glGetProgramInfoLog(programObjectId));
        }//check whether the link failed or succeeded
        if(linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);//If the linking failed, delete the program object.
            if(LoggerConfig.ON) Log.w(TAG,"Linking of program failed.");
            return 0;
        }

        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId){
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId,GL_VALIDATE_STATUS,validateStatus,0);
        Log.v(TAG,"Results of validating program: "+validateStatus[0]+"\nLog: "+glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        //Compile the shaders.
        int vertexShader = compileShader(GL_VERTEX_SHADER,vertexShaderSource);
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER,fragmentShaderSource);

        int program = linkProgram(vertexShader, fragmentShader);

        if(LoggerConfig.ON) validateProgram(program);

        return program;
    }
}

