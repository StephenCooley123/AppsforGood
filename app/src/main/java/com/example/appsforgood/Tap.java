package com.example.appsforgood;

public class Tap{
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



    public String startKey() {
        return "tap{";
    }


    public String endKey() {
        return "}";
    }
}
