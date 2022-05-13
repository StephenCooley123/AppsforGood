package com.example.appsforgood;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Interaction {

    int key;

    Long duration;
    ArrayList<Tap> taps;
    ArrayList<String> touchedWords = new ArrayList<String>();



    public Interaction(long duration, ArrayList<String> touchedWords ) {
        this.duration = duration;
        for(String touchedWord : touchedWords) {
            this.touchedWords.add(touchedWord);
        }
    }
    public String getKey() {
        return Integer.toString(key);

    }
    public void setKey(int key) {
        this.key = key;
    }


    //format is KEY, DURATION, WORD1|WORD2|WORD3
    public String toString() {
        String s = "";
        s = s + key + CSVParser.csvSeparatorChar;
        s = s + duration + CSVParser.csvSeparatorChar;
        for(String w : touchedWords) {
            s = s + w + CSVParser.listSeparatorChar;
        }
        s = s.substring(0, s.length() - 1);
        return s;
    }

    public Long getDuration() {
        return duration;
    }



}
