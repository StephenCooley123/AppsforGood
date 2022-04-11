package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.*;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //TO SAVE CHANGES TO MASTER
    // Commit to save local
    //Push to save to your cloud branch
    //in menu in bottom right: master -> update
    //master -> merge into current
    //VCS -> Git -> Create Pull Request to send to master
    //TO MERGE MASTER INTO CURRENT:
    // Bottom Right Menu -> master -> update
    //          "       -> master -> merge into current
    //to merge changes from someone else, fetch first
    List<Word> words = new ArrayList<Word>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readWords();
        generateTestWord();
        System.out.println(words.get(0));
    }

    /**
     * Interacts with the database and reads the words on startup
     */
    private void readWords() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AFGWords").build();
        WordDAO wordDAO = db.wordDao();
        List<Word> words = wordDAO.getAll();
        this.words =  words;
    }

    /**
     * Triggered by the Edit Words button
     */
    public void editWords() {

    }

    /**
     * Triggered by the Start button
     * Starts the game; shows a word and answers
     */
    public void start() {

    }

    private void generateTestWord() {
        String s = "";
        for (int i = 0; i < 5; i++) {
            s = s + (char)(((int) (Math.random() * 26)) + 'a');
        }
        Word w = new Word(s);
        w.addTag("tag1");
        w.addTag("tag2");
        w.addTag("tag3");
        words.add(w);
    }



}