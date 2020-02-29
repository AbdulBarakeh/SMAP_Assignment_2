package com.example.wordlearnerassignment_smap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
private Button cancel;
private Button OK;

private TextView NameOfWord_Edit_T;
private EditText  NotesOfWord_Edit_T;
private TextView RatingOfWord_Edit_T;
private SeekBar RatingBar_Edit_T;
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
        if (savedInstanceState != null){
            picOfWord = receivedFromDetail.getIntExtra("PicOfWord",R.drawable.imagenotfound);
            nameOfWord = receivedFromDetail.getStringExtra( "NameOfWord" );
            pronounOfWord = receivedFromDetail.getStringExtra( "PronounOfWord" );
            descripOfWord = receivedFromDetail.getStringExtra( "DescripOfWord" );
            notesOfWord = savedInstanceState.getString("notes");
            ratingOfWord = savedInstanceState.getDouble("rating");
        }
        else{
            picOfWord = receivedFromDetail.getIntExtra("PicOfWord",R.drawable.imagenotfound);
            nameOfWord = receivedFromDetail.getStringExtra( "NameOfWord" );
            pronounOfWord = receivedFromDetail.getStringExtra( "PronounOfWord" );
            descripOfWord = receivedFromDetail.getStringExtra( "DescripOfWord" );
            notesOfWord = receivedFromDetail.getStringExtra( "NotesOfWord" );
            ratingOfWord = receivedFromDetail.getDoubleExtra( "RatingOfWord",0 );
        }



        NameOfWord_Edit_T = findViewById( R.id.NameOfWord_Edit);
        NotesOfWord_Edit_T = findViewById( R.id.NotesInput_Edit);
        RatingOfWord_Edit_T = findViewById( R.id.Rating_Edit);
        RatingBar_Edit_T = findViewById(R.id.Seekbar_Edit);



        NameOfWord_Edit_T.setText(nameOfWord);
        NotesOfWord_Edit_T.setText(notesOfWord);
        RatingOfWord_Edit_T.setText(String.valueOf(ratingOfWord));
        // Scalability of the trigger position, and placing in to corespond with the current rating.
        RatingBar_Edit_T.setMax(100);
        RatingBar_Edit_T.setProgress((int)ratingOfWord*10);

        
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
                notesOfWord = NotesOfWord_Edit_T.getText().toString();
                Intent returnValues = new Intent();
                returnValues.putExtra("pic",picOfWord);
                returnValues.putExtra("name",nameOfWord);
                returnValues.putExtra("pronoun",pronounOfWord);
                returnValues.putExtra("descrip",descripOfWord);
                returnValues.putExtra("notes",notesOfWord);
                returnValues.putExtra("rating",ratingOfWord);
                setResult(98, returnValues);
                finish();
            }
        });
//SRC: DemoUI-Project SMAP-Course F20
        RatingBar_Edit_T.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar , int progress , boolean fromUser) {
                double doubleProgress = ((double) progress)/10;
                RatingOfWord_Edit_T.setText(String.valueOf(doubleProgress));
                ratingOfWord = Double.parseDouble(RatingOfWord_Edit_T.getText().toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("rating",ratingOfWord);
        outState.putString("notes",notesOfWord);
    }
}
