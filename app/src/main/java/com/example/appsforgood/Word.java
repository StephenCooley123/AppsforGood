package com.example.appsforgood;

import android.widget.ImageButton;

import java.util.ArrayList;

public class Word implements Printable {
    String word;
    ArrayList<Interaction> interactions;
    //ArrayList<ImageButton> images = new ArrayList<ImageButton>();


    public Word(String str) {
        word = str;
    }
    public String getWord() {

        return word;

    }

    @Override
    public String startKey() {
        return "word{";
    }

    @Override
    public String endKey() {
        return "}";
    }


    public String toString() {
        return null;
    }

    public void addInteraction(Interaction i) {
        interactions.add(i);
    }
}
