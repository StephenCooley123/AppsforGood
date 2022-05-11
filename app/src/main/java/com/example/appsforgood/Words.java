package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Words extends AppCompatActivity {

        ListView alistviewWords;
        ArrayList<String> words = new ArrayList<String>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            for(Word n: MainActivity.getWords()){
                words.add(n.getWord());
            }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_editwords);
            alistviewWords= (ListView)findViewById(R.id.alistviewWords);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_words, R.id.textView,words);
            alistviewWords.setAdapter(arrayAdapter);
            Intent intent = getIntent();
        }

}