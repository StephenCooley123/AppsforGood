package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

public class Categorylistview extends AppCompatActivity {

    ListView tags;
    ArrayList<String> thetags = new ArrayList<String>();
    ArrayList<String> selectedtags;
    String catname;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorylistview);

        for (Word w : MainActivity.words) {
            ArrayList<String> wordtags = w.getTags();
            for (String t : wordtags) {
                if (!thetags.contains(t)) {
                    thetags.add(t);
                }
            }
        }
        tags = (ListView) findViewById(R.id.alistviewtags);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_tagsincategory, R.id.tag, thetags);
        tags.setAdapter(arrayAdapter);

        Intent intent = getIntent();
        pos =intent.getIntExtra("taglistpos",0);
        selectedtags=SettingsModel.tags.get(pos);


        for (int i=0; i < thetags.size(); i++) {
                if (selectedtags.contains(thetags.get(i))) {
                    //tag.getChidAt(i).setBackgroundColor(Color.parseColor("#00ff00"));
                    //Doesn't set the colors of the previous selects, needs fix.
                }
        }

        tags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTag(thetags.get(position));
                view.setBackgroundColor(Color.parseColor("#00ff00"));
            }
        });


    }
    public void selectedTag(String tag) {
    if(!selectedtags.contains(tag)){
        selectedtags.add(tag);
    }
    }

    public void returnClick(View v){
        EditText categorynames = findViewById(R.id.cn);
        catname= categorynames.getText().toString();
        SettingsModel.tags.set(pos,selectedtags);
        if(!(catname.isEmpty())){SettingsModel.Categorynames.set(pos,catname);}
        Intent intent = new Intent(this,Categories.class);
        startActivity(intent);
    }
}