package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //TO SAVE CHANGES TO MASTER
    // Commit to save local
    //Push to save to your cloud branch
    //in menu in bottom right: master -> update
    //master -> merge into current
    //VCS -> Git -> Create Pull Request to send to master
    //TO MERGE MASTER INTO CURRENT:
    // Bottom Right Menu -> master -> update
    //          "       -> master -> merge into current

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextWord(View v){
        Intent intent = new Intent(this,HomePage.class);
        intent.putExtra("condition",true);
        startActivity(intent);
    }

    public void Description(View v){
        Intent intent = new Intent(this,HomePage.class);
        intent.putExtra("condition",false);
        startActivity(intent);
    }

    public void Parental(View v){

    }

}