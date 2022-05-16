package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {
boolean condition;
boolean edit;

ListView alistview;
ArrayList<String> wordLists = SettingsModel.Categorynames;
ImageButton button;
ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        wordLists=SettingsModel.Categorynames;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        alistview = (ListView)findViewById(R.id.alistviewWords);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.listinwordlists, R.id.wordtext, wordLists);
        button=findViewById(R.id.AddVocabWord);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsModel.Categorynames.add("Category "+(wordLists.size()+1));
                ArrayList<String> n = new ArrayList<String>();
                SettingsModel.tags.add(n);
                arrayAdapter.notifyDataSetChanged();
            }
        };
        button.setOnClickListener(onClickListener);
        alistview.setAdapter(arrayAdapter);


        alistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edittags(position);
            }
        });
    }

    public void edittags(int pos){
        Intent intent = new Intent(this, Categorylistview.class);
        intent.putExtra("taglistpos", pos);
        startActivity(intent);
    }

    public void toHome(View v){
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}