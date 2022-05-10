package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        }

    public void toLists(View v){
        Intent intent = new Intent(this, Categories.class);
        intent.putExtra("edit",true);
        startActivity(intent);
    }
}