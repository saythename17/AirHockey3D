package com.icandothisallday2021.airhockey3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class TextureHelper {
    static String TAG="TextureHelper";

    //Read in an image file frmo our resources folder and load the image data into OpenGL.
    public static int loadTexture(Context context, int resourceId){
        final int[] textureObjectIds = new int[1];
        GLES20.glGenTextures(1, textureObjectIds, 0);
        if(textureObjectIds[0] == 0){//log the error == return 0
            if(LoggerConfig.ON) Log.w(TAG, "Could not generate a new OpenGL texture object");
            return 0;
        }

        //Android's built-in bitmap decoder
        //To decompress the image files into a form that OpenGL understands.
        //(OpenGL can't read data from a PNG or JPEG... It needs the raw data in an uncompressed form.)
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;//This tells Android that I want the original image data instead of a scaled version of the data.

        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        //This call will decode the image into bitmap or will return null--if it failed.

        if(bitmap == null){
            if (LoggerConfig.ON) Log.w(TAG, "Resource ID "+resourceId+" could not be decoded.");
            GLES20.glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }

        //I need to tell OpenGL that future texture calls should be applied to this texture object.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0]);
        // <parameter>
        //GL_TEXTURE_@D :  tells OpenGL that this should be treated as a two-dimensional texture.
        //textureObjectIds[0] : tells OpenGL which texture object ID to bind to.

        /*⭐️Texture Filtering
        * minification : happens when we try to cram several texels onto the same fragment.
        * magnification : happens when we spread one texel across many fragment.
        *
        * Nearest-neighbor filtering :
        * minify- many of the details will be lost, as we don't have enough fragment for all of the texels.
        * magnify- look rather blocky, visible as a small square
        *
        * Bilinear interpolation :
        * minify- lose a lot of detail.
        * magnify- smooth transitions between pixels. like gradation, still some blockiness
        *
        * ❕Mipmapping :
        * generates an optimized set of textures at different size.
        * At rendertime, OpenGL will select the most appropriate level for each fragment based on the number of texels per fragment.
        *
        * Trilinear Filtering :
        * interpolate between the two closest mipmap levels, using a total of eight texels per fragment-> more smoother*/
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);//refers to minification
        // LINEAR_MIPMAP_LINEAR : to use trilinear filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);//refers to magnification
        // LINEAR : to use bilinear filtering

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        //OpenGL reads in the bitmap data defined by bitmap and copy it over into the texture object that is currently bound.

        //Now that the data's been loaded into OpenGL->no longer need to keep the Android bitmap->release the data immediately
        bitmap.recycle();

        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);//OpenGL generates all of the necessary levels

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
        //finished loading texture-> unbind from the current texture, so we don't changes with other texture calls

        return textureObjectIds[0];
        //get a textureID--we can use as a reference to this texture
        //get 0--load filed
    }//This method will return the ID of the loaded OpenGL texture.
}
