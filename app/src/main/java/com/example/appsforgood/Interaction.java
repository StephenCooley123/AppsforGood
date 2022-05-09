package com.example.appsforgood;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Interaction {
    public static final long timeoutLength = 30*1000;
    int key;
    boolean timeout;
    Long time;
    ArrayList<Tap> taps;
    ArrayList<Word> orderedAnswers = new ArrayList<Word>();



    public Interaction() {

    }
    public String getKey() {
        return Integer.toString(key);

    }
    public void setKey(int key) {
        this.key = key;
    }

    public void setTime(long timeLength) {
        this.time = timeLength;
        if(timeLength >= timeoutLength) {
            timeout = true;
        }
    }

    public void addWord(Word w) {
        orderedAnswers.add(w);
    }


}
