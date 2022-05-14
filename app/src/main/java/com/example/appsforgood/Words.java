package com.example.appsforgood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
            for (Word n : MainActivity.getWords()) {
                words.add(n.getWord());
            }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_editwords);
            alistviewWords= (ListView)findViewById(R.id.alistviewWords);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_words, R.id.wordtext,words);
            alistviewWords.setAdapter(arrayAdapter);
            Intent intent = getIntent();
            alistviewWords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    toDeleteWord(words.get(position));
                    view.setBackgroundColor(Color.parseColor("#ff0000"));
                }
            });
        }

    public void toDeleteWord(String deletedWord){
        for(Word n: MainActivity.getWords()){
            String wordn=n.toString();
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
            for(Word n:deletingwords){
                if(!SettingsModel.prevdeletingwords.contains(n)){
                    SettingsModel.prevdeletingwords.add(n);
                }
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
}