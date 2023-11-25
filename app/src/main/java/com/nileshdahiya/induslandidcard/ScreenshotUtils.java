package com.nileshdahiya.induslandidcard;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotUtils {

    public static Bitmap captureView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static void saveBitmapToFile(Bitmap bitmap, String fileName) {
        String myFolder = "/myCustomFolder";
        String fileNames = "myImage.png";

//        bitmap.setDrawingCacheEnabled(true);
        File directory = new File(Environment.getExternalStorageDirectory() + myFolder);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File imageFile = new File(directory, fileName);
        // Write the bitmap to file
        try {
            FileOutputStream outStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Disable drawing cache
//        rl.setDrawingCacheEnabled(false);
    }
}

