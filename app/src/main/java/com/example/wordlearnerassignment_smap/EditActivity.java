package com.example.wordlearnerassignment_smap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
private Button cancel;
private Button OK;

private ImageView PictureOfWord_Edit_T;
private TextView NameOfWord_Edit_T;
private TextView  PronounOfWord_Edit_T;
private TextView  DescripOfWord_Edit_T;
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

        picOfWord = receivedFromDetail.getIntExtra("PicOfWord",R.drawable.imagenotfound);
        nameOfWord = receivedFromDetail.getStringExtra( "NameOfWord" );
        pronounOfWord = receivedFromDetail.getStringExtra( "PronounOfWord" );
        descripOfWord = receivedFromDetail.getStringExtra( "DescripOfWord" );
        notesOfWord = receivedFromDetail.getStringExtra( "NotesOfWord" );
        ratingOfWord = receivedFromDetail.getDoubleExtra( "RatingOfWord",0 );

        //PictureOfWord_Edit_T = findViewById( R.id.PictureOfWord_Edit);
        NameOfWord_Edit_T = findViewById( R.id.NameOfWord_Edit);
        //PronounOfWord_Edit_T = findViewById( R.id.PronounOfWord_Edit);
        //DescripOfWord_Edit_T = findViewById( R.id.DescriptionOfWord_Edit);
        NotesOfWord_Edit_T = findViewById( R.id.NotesInput_Edit);
        RatingOfWord_Edit_T = findViewById( R.id.Rating_Edit);
        RatingBar_Edit_T = findViewById(R.id.Seekbar_Edit);

        //PictureOfWord_Edit_T.setImageResource(picOfWord);
        NameOfWord_Edit_T.setText(nameOfWord);
        //PronounOfWord_Edit_T.setText(pronounOfWord);
        //DescripOfWord_Edit_T.setText(descripOfWord);
        NotesOfWord_Edit_T.setText(notesOfWord);
        RatingOfWord_Edit_T.setText(String.valueOf(ratingOfWord));
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
                Intent returnValues = new Intent();
                returnValues.putExtra("pic",picOfWord);
                returnValues.putExtra("name",nameOfWord);
                returnValues.putExtra("pronoun",pronounOfWord);
                returnValues.putExtra("descrip",descripOfWord);
                returnValues.putExtra("notes",notesOfWord);
                returnValues.putExtra("rating",ratingOfWord);
                setResult(98, returnValues);
                finish();
                //Remember to save changes
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
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        //SRC: https://developer.android.com/training/keyboard-input/style
        NotesOfWord_Edit_T.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v , int actionId , KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    NotesOfWord_Edit_T.setText(v.getText());
                    notesOfWord = NotesOfWord_Edit_T.getText().toString();
                    handled = true;
                }
                return handled;
            }
        });



    }

}
