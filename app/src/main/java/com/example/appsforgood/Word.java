package com.example.appsforgood;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;

@Entity(tableName = "words")
public class Word {
    @PrimaryKey
    public String word;

    ArrayList<Interaction> interactions;
    //ArrayList<ImageButton> images = new ArrayList<ImageButton>();


    public Word(String str) {
        word = str;
    }

    public String getWord() {
        return word;

    }


    public String toString() {
        return word;
    }

    public void addInteraction(Interaction i) {
        interactions.add(i);
    }


}
