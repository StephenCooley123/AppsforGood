package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class choosecategory extends AppCompatActivity {

    ListView alistview;
    ArrayList<String> wordLists = SettingsModel.Categorynames;
    ArrayAdapter<String> arrayAdapter;
    boolean condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosecategory);
        alistview = (ListView)findViewById(R.id.alistviewWords);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.listinwordlists, R.id.wordtext, wordLists);
        alistview.setAdapter(arrayAdapter);

        Intent intent =getIntent();
        condition =intent.getBooleanExtra("condition",true);

        alistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changePage(position);
            }
        });
    }

    public void normalChange(View v){
        Intent intent = new Intent(this,FlashcardActivity.class);
        intent.putExtra("condition",condition);
        intent.putExtra("edit",false);
        startActivity(intent);
    }

    public void changePage(int pos){
        Intent intent = new Intent(this,FlashcardActivity.class);
        ArrayList<String> tags =SettingsModel.tags.get(pos);
        intent.putExtra("tags",tags);
        intent.putExtra("condition",condition);
        intent.putExtra("edit",true);
        startActivity(intent);
    }

}