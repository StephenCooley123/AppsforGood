package com.example.appsforgood;

import java.util.ArrayList;

public class Interaction extends Printable {
    boolean timeout;
    Long time;
    ArrayList<Tap> taps;
    ArrayList<Word> answers;
    Word correctAnswer;
    Word selectedAnswer;




    public String toString() {

        return "";
    }

    public static Interaction parseInteraction(String input) {
        return null;
    }

    @Override
    public String startKey() {
        return "interaction{";
    }

    @Override
    public String endKey() {
        return "}";
    }
}
