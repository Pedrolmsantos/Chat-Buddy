package com.example.socialapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;

public class ImageConversion {
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public static void writeToFile(Context context, String filename, byte[] data) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fileOutputStream.write(data);
            fileOutputStream.close();
        }
        catch (IOException e) {
        }
    }
    public static byte[] loadFile(Context context, String filename){
        ByteBuffer res;
        int total_size, total_read;
        try {
            FileInputStream fis = context.openFileInput(filename);
            total_size = fis.available();
            total_read = 0;
            res = ByteBuffer.allocate(total_size);
            byte[] chunk = new byte[total_size];
            while(total_read < total_size){
                int read = fis.read(chunk);
                res.put(chunk,0,read);
                total_read += read;
            }
            fis.close();
            return res.array();
        }catch (Exception e){
            return null;
        }
    }
}
