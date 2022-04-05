package com.example.appsforgood;

public class Word extends Printable {
    String word;

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
}
