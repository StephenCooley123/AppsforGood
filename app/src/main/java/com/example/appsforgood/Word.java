package com.example.appsforgood;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.Toast;



import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;

//@Entity(tableName = "words")
public class Word {
    //@PrimaryKey
    private String word;

    private ArrayList<Interaction> interactions = new ArrayList<Interaction>();
    private ArrayList<String> tags = new ArrayList<String>();
    private ArrayList<LoadedImage> images = new ArrayList<LoadedImage>();


    public Word(String str) {
        word = str;
    }

    public String getWord() {
        return word;

    }

    @Override
    public String toString() {

        return word;
    }

    public void addInteraction(Interaction i) {
        interactions.add(i);
    }

    public void addTag(String tag) {
        boolean contains = false;
        for(String s : tags) {
            if(s.equals(tag)) {
                contains = true;
            }
        }
        if(!contains) {
            tags.add(tag);
        }
    }
    public boolean containsTag(String tag) {
        for(String s : tags) {
            if(s.equals(tag)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public ArrayList<LoadedImage> getImages() {
        return images;
    }

    public ArrayList<Interaction> getInteractions() {
        return interactions;
    }

}
