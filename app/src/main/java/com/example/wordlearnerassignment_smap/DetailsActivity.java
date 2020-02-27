package com.example.wordlearnerassignment_smap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
private Button cancel;
private Button edit;
private ImageView PictureOfWord_Detail_T;
private TextView  NameOfWord_Detail_T;
private TextView  PronounOfWord_Detail_T;
private TextView  DescripOfWord_Detail_T;
private TextView  NotesOfWord_Detail_T;
private TextView RatingOfWord_Detail_T;

private int picOfWord;
private String nameOfWord;
private String pronounOfWord;
private String descripOfWord;
private String notesOfWord;
private double ratingOfWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent receivedData = getIntent();
        picOfWord = receivedData.getIntExtra("PicOfWord",R.drawable.imagenotfound);
        nameOfWord = receivedData.getStringExtra( "NameOfWord" );
        pronounOfWord = receivedData.getStringExtra( "PronounOfWord" );
        descripOfWord = receivedData.getStringExtra( "DescripOfWord" );
        notesOfWord = receivedData.getStringExtra( "NotesOfWord" );
        ratingOfWord = receivedData.getDoubleExtra( "RatingOfWord",0 );

        PictureOfWord_Detail_T = findViewById( R.id.PictureOfWord_Detail);
        NameOfWord_Detail_T = findViewById( R.id.NameOfWord_Detail);
        PronounOfWord_Detail_T = findViewById( R.id.PronounOfWord_Detail);
        DescripOfWord_Detail_T = findViewById( R.id.DescriptionOfWord_Detail);
        NotesOfWord_Detail_T = findViewById( R.id.NotesOfWord_Detail);
        RatingOfWord_Detail_T = findViewById( R.id.RatingOfWord_Detail);

        PictureOfWord_Detail_T.setImageResource(picOfWord);
        NameOfWord_Detail_T.setText(nameOfWord);
        PronounOfWord_Detail_T.setText(pronounOfWord);
        DescripOfWord_Detail_T.setText(descripOfWord);
        NotesOfWord_Detail_T.setText(notesOfWord);
        RatingOfWord_Detail_T.setText(String.valueOf(ratingOfWord));

        cancel = findViewById(R.id.ACTIVITY_DETAIL_CANCEL_BUTTON);
        edit = findViewById(R.id.ACTIVITY_DETAIL_BUTTON_EDIT);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//Lifecycle!
                Intent GoToEdit = new Intent(DetailsActivity.this, EditActivity.class);
                //Send specific data with the new intent
                startActivity(GoToEdit);
            }
        });
    }
}
