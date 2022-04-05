package com.example.appsforgood;

public class Word extends Printable {
    String word;

    @Override
    public String startKey() {
        return "word{";
    }

    @Override
    public String endKey() {
        return "}";
    }
}
