package com.example.appsforgood;

import java.util.ArrayList;

public class SmallStorage {

    static ArrayList<String> wordLists = new ArrayList<String>();

    public static ArrayList<String> getListofLists(){
        return wordLists;
    }

    public static void addtowordLists(String s){
        wordLists.add(s);
    }
}
