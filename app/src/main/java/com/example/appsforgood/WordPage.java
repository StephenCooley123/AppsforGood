package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WordPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_page);

        Intent intent2 = getIntent();
        String word = intent2.getStringExtra("word");
        String questionString = intent2.getStringExtra("question");

        TextView Word = findViewById(R.id.Word);

        Word.setText(word);

        TextView question = findViewById(R.id.Question);
        question.setText(questionString);
    }

    public void returntoSlides(View v){
        Intent intent = new Intent(this, FlashcardActivity.class);
        intent.putExtra("condition",false);
        startActivity(intent);
    }
}