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
private int positionOfWord;
static final int BETWEEN_DETAIL_EDIT_RES = 98;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //Get all data either from detail or from the savedinstance.
        Intent receivedFromDetail = getIntent();
        if (savedInstanceState != null){
            picOfWord = receivedFromDetail.getIntExtra(         "PicOfWord_Sent_From_Detail"    ,R.drawable.imagenotfound);
            nameOfWord = receivedFromDetail.getStringExtra(     "NameOfWord_Sent_From_Detail"   );
            pronounOfWord = receivedFromDetail.getStringExtra(  "PronounOfWord_Sent_From_Detail");
            descripOfWord = receivedFromDetail.getStringExtra(  "DescripOfWord_Sent_From_Detail");
            notesOfWord = savedInstanceState.getString(           "saved_rating"                  );
            ratingOfWord = savedInstanceState.getDouble(          "saved_notes"                   );
            positionOfWord = savedInstanceState.getInt(           "saved_position"                );
        }
        else{
            picOfWord = receivedFromDetail.getIntExtra(         "PicOfWord_Sent_From_Detail"     ,R.drawable.imagenotfound);
            nameOfWord = receivedFromDetail.getStringExtra(     "NameOfWord_Sent_From_Detail"    );
            pronounOfWord = receivedFromDetail.getStringExtra(  "PronounOfWord_Sent_From_Detail" );
            descripOfWord = receivedFromDetail.getStringExtra(  "DescripOfWord_Sent_From_Detail" );
            notesOfWord = receivedFromDetail.getStringExtra(    "NotesOfWord_Sent_From_Detail"   );
            ratingOfWord = receivedFromDetail.getDoubleExtra(   "RatingOfWord_Sent_From_Detail"  ,0 );
            positionOfWord = receivedFromDetail.getIntExtra(    "PositionOfWord_Sent_From_Detail",13);
        }
        //Bind UI elements with local variables
        cancel = findViewById(R.id.ACTIVITY_EDIT_CANCEL_BUTTON2);
        OK = findViewById(R.id.ACTIVITY_EDIT_OK_BUT);
        NameOfWord_Edit_T = findViewById( R.id.NameOfWord_Edit);
        NotesOfWord_Edit_T = findViewById( R.id.NotesInput_Edit);
        RatingOfWord_Edit_T = findViewById( R.id.Rating_Edit);
        RatingBar_Edit_T = findViewById(R.id.Seekbar_Edit);

        //Set the data into the UI elements
        NameOfWord_Edit_T.setText(nameOfWord);
        NotesOfWord_Edit_T.setText(notesOfWord);
        RatingOfWord_Edit_T.setText(String.valueOf(ratingOfWord));

        // Scalability of the trigger position, and placing in to corespond with the current rating.
        RatingBar_Edit_T.setMax(100);
        RatingBar_Edit_T.setProgress((int)ratingOfWord*10);

        //Send data back to detail activity on click
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
                returnValues.putExtra("PicOfWord_Sent_From_Edit"        ,picOfWord);
                returnValues.putExtra("NameOfWord_Sent_From_Edit"       ,nameOfWord);
                returnValues.putExtra("PronounOfWord_Sent_From_Edit"    ,pronounOfWord);
                returnValues.putExtra("DescripOfWord_Sent_From_Edit"    ,descripOfWord);
                returnValues.putExtra("NotesOfWord_Sent_From_Edit"      ,notesOfWord);
                returnValues.putExtra("RatingOfWord_Sent_From_Edit"     ,ratingOfWord);
                returnValues.putExtra("PositionOfWord_Sent_From_Edit"   ,positionOfWord);
                setResult(BETWEEN_DETAIL_EDIT_RES, returnValues);
                finish();
            }
        });
        //SRC: DemoUI-Project SMAP-Course F20
        //seekbar functionality
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
        outState.putDouble( "saved_rating"  ,ratingOfWord);
        outState.putString( "saved_notes"   ,notesOfWord);
        outState.putInt(    "saved_position",positionOfWord);
    }
}
