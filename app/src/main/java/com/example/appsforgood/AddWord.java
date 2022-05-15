package com.example.appsforgood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.ArrayList;

public class AddWord extends AppCompatActivity implements View.OnClickListener {
Word W;
private static final int RESULT_LOAD_IMAGE=1;
ImageButton uploadimage;
String word;
boolean isword;
ArrayList<LoadedImage> images = new ArrayList<LoadedImage>();
ArrayList<String> questions = new ArrayList<String>();
ArrayList<String> tags= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getFilesDir();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword);
        Intent intent = getIntent();
        uploadimage=(ImageButton)findViewById(R.id.upimg);
        uploadimage.setOnClickListener(this);
    }

    public void EnterClicked(View v){
        EditText theword =findViewById(R.id.AddedWord);
        EditText q1=findViewById(R.id.q1);
        EditText q2=findViewById(R.id.q2);
        EditText cat1 =findViewById(R.id.cat1);
        EditText cat2 =findViewById(R.id.cat2);
        EditText cat3 =findViewById(R.id.cat3);

        String tag1 =cat1.getText().toString();
        String tag2 =cat2.getText().toString();
        String tag3 =cat3.getText().toString();
        word = theword.getText().toString();
        String ques1 = q1.getText().toString();
        String ques2 = q2.getText().toString();

        if(!ques1.isEmpty()){
            questions.add(ques1);
        }
        if(!ques2.isEmpty()){
            questions.add(ques2);
        }

        if(!tag1.isEmpty()){
            tags.add(tag1);
        }
        if(!tag2.isEmpty()){
            tags.add(tag2);
        }
        if(!tag3.isEmpty()){
            tags.add(tag3);
        }

        if(!word.isEmpty()){
            isword=true;
            W = new Word (word,questions,tags);
        }
        else{
            isword=false;
        }

        if(isword){ W.setImages(images);
        MainActivity.addtoMainWords(W);}
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null){
            Uri selectedImage=data.getData();

            Bitmap bitmap = null;
            if (Build.VERSION.SDK_INT >= 29) {
                ImageDecoder.Source source = ImageDecoder.createSource(getApplicationContext().getContentResolver(), selectedImage);
                try {
                    bitmap = ImageDecoder.decodeBitmap(source);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            LoadedImage newimage =new LoadedImage(bitmap,"img");
            images.add(newimage);
        }
    }


}