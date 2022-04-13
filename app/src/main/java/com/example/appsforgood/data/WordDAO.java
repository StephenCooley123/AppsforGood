package com.example.appsforgood.data;

import androidx.room.*;

import java.util.List;


public interface WordDAO {
   // @Query("SELECT * FROM words")
   // List<Word> getAll();

    //COME BACK AND CHANGE THIS TO SOMETHING WHICH INDICATES THE USER OF A CONFLICT, or else do it manually

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    //void insertWord(Word w);


    public abstract String startKey();
    public abstract String endKey();
}
