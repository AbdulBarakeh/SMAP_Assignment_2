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
private TextView Name;
private EditText Notes;
private TextView Rating;
private SeekBar RatingBar;

static final int BETWEEN_DETAIL_EDIT_RES = 98;
WordTemplate word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //Get all data either from detail or from the savedinstance.
        Intent receivedFromDetail = getIntent();
        if (savedInstanceState != null){
            word = savedInstanceState.getParcelable("word");
        }
        else{
            word = receivedFromDetail.getParcelableExtra("word");
        }
        //Bind UI elements with local variables
        cancel = findViewById(R.id.ACTIVITY_EDIT_CANCEL_BUTTON2);
        OK = findViewById(R.id.ACTIVITY_EDIT_OK_BUT);
        Name = findViewById( R.id.NameOfWord_Edit);
        Notes = findViewById( R.id.NotesInput_Edit);
        Rating = findViewById( R.id.Rating_Edit);
        RatingBar = findViewById(R.id.Seekbar_Edit);

        //Set the data into the UI elements
        Name.setText(word.getName());
        Notes.setText(word.getNotes());
        Rating.setText(String.valueOf(word.getRating()));

        // Scalability of the trigger position, and placing in to corespond with the current rating.
        RatingBar.setMax(100);
        RatingBar.setProgress((int)word.getRating()*10);

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
                Intent returnValues = new Intent();
                word.setNotes(Notes.getText().toString());
                returnValues.putExtra("word",word);
                setResult(BETWEEN_DETAIL_EDIT_RES, returnValues);
                finish();
            }
        });

        //SRC: DemoUI-Project SMAP-Course F20
        //seekbar functionality
        RatingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar , int progress , boolean fromUser) {
                double doubleProgress = ((double) progress)/10;
                Rating.setText(String.valueOf(doubleProgress));
                word.setRating(Double.parseDouble(Rating.getText().toString()));
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
        outState.putParcelable("word",word);
    }
}
