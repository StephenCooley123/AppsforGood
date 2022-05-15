package com.example.appsforgood;

import java.util.ArrayList;
import java.util.List;

public class SettingsModel {
    //Relates to the Categories of Words
    static ArrayList<Word> prevdeletingwords =new ArrayList<Word>();
    static ArrayList<String> wordLists = new ArrayList<String>();

    public static ArrayList<String> getListofLists(){
        return wordLists;
    }

    public static void addtowordLists(String s){
        wordLists.add(s);
    }
}
