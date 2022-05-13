package com.example.appsforgood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class LoadedImage {
    Bitmap image;
    String reference;
    public LoadedImage(Bitmap image, String reference) {
        this.image = image;
        this.reference = reference;
    }

    @Override
    public String toString() {
        return reference;
    }

    public Bitmap getImage(){
        return image;
    }
}
