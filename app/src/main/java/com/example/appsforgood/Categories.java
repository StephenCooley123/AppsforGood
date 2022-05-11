package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {
boolean condition;
boolean edit;

ListView alistview;
ArrayList<String> wordLists = Controller.getListofLists();
ImageButton button;
ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        alistview = (ListView)findViewById(R.id.alistviewWords);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.listinwordlists, R.id.textView, wordLists);
        button=findViewById(R.id.AddVocabWord);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.addtowordLists("New Category");
                arrayAdapter.notifyDataSetChanged();
            }
        };
        button.setOnClickListener(onClickListener);
        alistview.setAdapter(arrayAdapter);

        Intent intent = getIntent();
        edit=intent.getBooleanExtra("edit",true);

        if(!edit){
            condition=intent.getBooleanExtra("condition",true);
        }
    }

    public void buttonClick(View v){
        if(edit){
            toEdit();
        }
        else{
            toFlashCard();
        }
    }
    public void toFlashCard(View v){
        Intent intent = new Intent(this, FlashcardActivity.class);
        intent.putExtra("condition",condition);
    }

    public void toEdit(){


    }
    public void toFlashCard(){

    }

}