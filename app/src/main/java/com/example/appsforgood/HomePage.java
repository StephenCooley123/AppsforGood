package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

    }

    public void changePage(View v){
        String word = "Text-Edit-";

        Intent intent = getIntent();
        boolean state = intent.getBooleanExtra("condition", true);

        if(state){
            Intent intent1 = new Intent(this, HomePage.class);
            intent1.putExtra("condition",true);
            startActivity(intent1);
        }

        else{
            Intent intent2 = new Intent(this, WordPage.class);
            intent2.putExtra("word",word);
            startActivity(intent2);
        }
    }

}