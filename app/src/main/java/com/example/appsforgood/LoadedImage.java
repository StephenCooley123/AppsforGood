package com.example.appsforgood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class LoadedImage {
    Bitmap image;
    String filepath;
    public LoadedImage(String filepath) {
        this.filepath = filepath;
        File file = new File(filepath);
        image = BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    @Override
    public String toString() {
        return filepath;
    }
}
