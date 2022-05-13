package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AddWord extends AppCompatActivity {
Word W;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword);
        Intent intent = getIntent();

        EditText theword =findViewById(R.id.AddedWord);
        EditText theactivity=findViewById(R.id.editActivity);
        EditText thequestion=findViewById(R.id.editQuestion);

        String word = theword.getText().toString();
        String activity = theactivity.getText().toString();
        String question = thequestion.getText().toString();

        W = new Word(word);
        W.defineActivity(activity);
        W.defineQuestion(question);
    }

    public void EnterClicked(View v){
        MainActivity.addtoMainWords(W);
        Intent intent = new Intent(this,MainActivity.class);
    }


}