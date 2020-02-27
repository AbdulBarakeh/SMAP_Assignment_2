package com.example.wordlearnerassignment_smap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
private Button cancel;
private Button OK;

private ImageView PictureOfWord_Edit_T;
private TextView NameOfWord_Edit_T;
private TextView  PronounOfWord_Edit_T;
private TextView  DescripOfWord_Edit_T;
private TextView  NotesOfWord_Edit_T;
private TextView RatingOfWord_Edit_T;

private int picOfWord;
private String nameOfWord;
private String pronounOfWord;
private String descripOfWord;
private String notesOfWord;
private double ratingOfWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent receivedFromDetail = getIntent();

        //picOfWord = receivedFromDetail.getIntExtra("PicOfWord",R.drawable.imagenotfound);
        nameOfWord = receivedFromDetail.getStringExtra( "NameOfWord" );
        //pronounOfWord = receivedFromDetail.getStringExtra( "PronounOfWord" );
        //descripOfWord = receivedFromDetail.getStringExtra( "DescripOfWord" );
        notesOfWord = receivedFromDetail.getStringExtra( "NotesOfWord" );
        ratingOfWord = receivedFromDetail.getDoubleExtra( "RatingOfWord",0 );

        //PictureOfWord_Edit_T = findViewById( R.id.PictureOfWord_Edit);
        NameOfWord_Edit_T = findViewById( R.id.NameOfWord_Edit);
        //PronounOfWord_Edit_T = findViewById( R.id.PronounOfWord_Edit);
        //DescripOfWord_Edit_T = findViewById( R.id.DescriptionOfWord_Edit);
        NotesOfWord_Edit_T = findViewById( R.id.NotesInput_Edit);
        RatingOfWord_Edit_T = findViewById( R.id.Rating_Edit);

        //PictureOfWord_Edit_T.setImageResource(picOfWord);
        NameOfWord_Edit_T.setText(nameOfWord);
        //PronounOfWord_Edit_T.setText(pronounOfWord);
        //DescripOfWord_Edit_T.setText(descripOfWord);
        NotesOfWord_Edit_T.setText(notesOfWord);
        RatingOfWord_Edit_T.setText(String.valueOf(ratingOfWord));

        cancel = findViewById(R.id.ACTIVITY_EDIT_CANCEL_BUTTON2);
        OK = findViewById(R.id.ACTIVITY_EDIT_OK_BUT);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Remember to save changes
            }
        });
    }
}
