package com.example.appsforgood;

import java.util.ArrayList;

public class SettingsModel {
    //Relates to the Categories of Words
    static ArrayList<Word> deletedwords = new ArrayList<>();
    static ArrayList<String> wordLists = new ArrayList<String>();

    public static ArrayList<String> getListofLists(){
        return wordLists;
    }

    public static void addtowordLists(String s){
        wordLists.add(s);
    }

    public static ArrayList<Word> getDeletedwords(){ return deletedwords;}

    //Holds the words that won't be parsed and added to the list of words in Main Activity
    public static void addedtodeletedwords(ArrayList<Word> deletes){
        for(Word n: deletes){
            deletedwords.add(n);
        }
    }
}
