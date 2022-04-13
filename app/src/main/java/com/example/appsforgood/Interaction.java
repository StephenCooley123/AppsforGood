package com.example.appsforgood;

import java.util.ArrayList;

public class Interaction {
    int key;
    boolean timeout;
    Long time;
    ArrayList<Tap> taps;
    ArrayList<Word> answers;
    Word correctAnswer;
    Word selectedAnswer;

    public Interaction() {

    }
    public String getKey() {
        return Integer.toString(key);

    }
    public void setKey(int key) {
        this.key = key;
    }


}
