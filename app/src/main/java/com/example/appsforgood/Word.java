package com.example.appsforgood;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;

//@Entity(tableName = "words")
public class Word {
    //@PrimaryKey
    private String word;

    private ArrayList<Interaction> interactions = new ArrayList<Interaction>();
    private ArrayList<String> tags = new ArrayList<String>();
    private ArrayList<LoadedImage> images = new ArrayList<LoadedImage>();
    private ArrayList<String> questions = new ArrayList<String>();

    //THESE METHODS ARE ALMOST EXCLUSIVELY GETTERS AND SETTERS, AS WORD IS A DATA STRUCTURE

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

    //checks for unique tags and adds one
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

    //essentially a .contains()
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

    public void setImages(ArrayList<LoadedImage> wordImages) {
        images = wordImages;
    }
    public LoadedImage RandomImage(){

        return images.get((int) (Math.random()*images.size()));
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    //performs an arraycopy via shallow copy
    public void setInteractions(ArrayList<Interaction> interactions) {
        for(Interaction i : interactions) {
            this.interactions.add(i);
        }

    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }



}
