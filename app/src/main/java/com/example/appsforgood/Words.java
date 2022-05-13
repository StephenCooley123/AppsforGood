package com.example.appsforgood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Words extends AppCompatActivity {

        ListView alistviewWords;
        ArrayList<String> words = new ArrayList<String>();
       ArrayList<Word> deletingwords =new ArrayList<Word>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            for(Word n: MainActivity.getWords()){
                words.add(n.getWord());
            }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_editwords);
            alistviewWords= (ListView)findViewById(R.id.alistviewWords);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_words, R.id.wordtext,words);
            alistviewWords.setAdapter(arrayAdapter);
            Intent intent = getIntent();
        }

    public void toDeleteWord(View v){
        ImageButton deletebutton =findViewById(R.id.deleteSymbol);
        deletebutton.setDrawingCacheBackgroundColor(Color.parseColor("#FF0000"));
        TextView deleteWord =findViewById(R.id.wordtext);
        String deletedWord = deleteWord.toString();
        System.out.println("IN TO DELETE WORD. WORD IS: " + deletedWord);
        for(Word n: MainActivity.getWords()){
            String wordn=n.getWord();
            if(wordn.equals(deletedWord)){
                deletingwords.add(n);
            }
        }
    }
        public void toAddWord(View v){
            Intent intent = new Intent(this, AddWord.class);
            startActivity(intent);
        }

        public void DeleteAllWords(View v){
            System.out.println(deletingwords);
            MainActivity.deletefromMainWords(deletingwords);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
}