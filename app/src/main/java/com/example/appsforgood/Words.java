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
       ArrayList<Word> existingwords = new ArrayList<Word>();


        @Override
        //Forms the list view and reads the position of the words to be deleted
        protected void onCreate(Bundle savedInstanceState) {
            for (Word n : MainActivity.getWords()) {
                words.add(n.getWord());
                if(!SettingsModel.prevdeletingwords.contains(n)){
                    existingwords.add(n);
                }
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
                    toDeleteWord(words.get(position),view);
                }
            });
        }

    public void toDeleteWord(String deletedWord, View v){
            //Adds the selected word to the words that will be deleted unless there are less than three words.

        for(Word n: MainActivity.getWords()){
            String wordn=n.toString();
            if(wordn.equals(deletedWord)){
                existingwords.remove(n);
               if(existingwords.size()>=3){
                   deletingwords.add(n);
                   v.setBackgroundColor(Color.parseColor("#ff0000"));
               }
            }
        }
    }
        public void toAddWord(View v){
                //Sedns the user to the addWords page
            Intent intent = new Intent(this, AddWord.class);
            startActivity(intent);
        }

        public void DeleteAllWords(View v){
                //Adds new words from deletingwords to the words in prevdeleting words and rewrites the words in Main Activity
            for(Word n:deletingwords){
                if(!SettingsModel.prevdeletingwords.contains(n)){
                    SettingsModel.prevdeletingwords.add(n);
                }
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
}