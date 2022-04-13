package com.example.appsforgood;

public class Tap implements Printable{
    char xySeparatorChar = '\t';
    double x;
    double y;
    public Tap(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Tap parseTap(String str) {
        return new Tap(0, 0);
    }

    public String toString() {
        return x + Character.toString(xySeparatorChar) + y;
    }


    @Override
    public String startKey() {
        return "tap{";
    }

    @Override
    public String endKey() {
        return "}";
    }
}
