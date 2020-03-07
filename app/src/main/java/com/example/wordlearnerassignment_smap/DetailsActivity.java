package com.example.wordlearnerassignment_smap;

import androidx.annotation.NonNull;
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
private ImageView PictureOfWord;
private TextView NameOfWord;
private TextView PronounOfWord;
private TextView DescripOfWord;
private TextView NotesOfWord;
private TextView RatingOfWord;

private int picOfWord;
private String nameOfWord;
private String pronounOfWord;
private String descripOfWord;
private String notesOfWord;
private double ratingOfWord;
private int positionOfWord;
static final int BETWEEN_LIST_DETAIL_RES = 99;
static final int BETWEEN_DETAIL_EDIT_RES = 98;
static final int BETWEEN_DETAIL_EDIT_REQ = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState != null){
            picOfWord = savedInstanceState.getInt(       "saved_pic"     );
            nameOfWord = savedInstanceState.getString(   "saved_name"    );
            pronounOfWord = savedInstanceState.getString("saved_pronoun" );
            descripOfWord = savedInstanceState.getString("saved_descrip" );
            notesOfWord = savedInstanceState.getString(  "saved_notes"   );
            ratingOfWord = savedInstanceState.getDouble( "saved_rating"  );
            positionOfWord = savedInstanceState.getInt(  "saved_position");
        }
        else {
            Intent receivedFromList = getIntent();
            picOfWord = receivedFromList.getIntExtra(       "PicOfWord_Sent_From_List"     , R.drawable.imagenotfound);
            nameOfWord = receivedFromList.getStringExtra(   "NameOfWord_Sent_From_List"    );
            pronounOfWord = receivedFromList.getStringExtra("PronounOfWord_Sent_From_List" );
            descripOfWord = receivedFromList.getStringExtra("DescripOfWord_Sent_From_List" );
            notesOfWord = receivedFromList.getStringExtra(  "NotesOfWord_Sent_From_List"   );
            ratingOfWord = receivedFromList.getDoubleExtra( "RatingOfWord_Sent_From_List"  , 0);
            positionOfWord = receivedFromList.getIntExtra(  "PositionOfWord_Sent_From_List", 13);
        }
        //Bind UI elements with local variables
        PictureOfWord = findViewById( R.id.PictureOfWord_Detail);
        NameOfWord = findViewById( R.id.NameOfWord_Detail);
        PronounOfWord = findViewById( R.id.PronounOfWord_Detail);
        DescripOfWord = findViewById( R.id.DescriptionOfWord_Detail);
        NotesOfWord = findViewById( R.id.NotesOfWord_Detail);
        RatingOfWord = findViewById( R.id.RatingOfWord_Detail);
        cancel = findViewById(R.id.ACTIVITY_DETAIL_CANCEL_BUTTON);
        edit = findViewById(R.id.ACTIVITY_DETAIL_BUTTON_EDIT);

        //Set the data into the UI elements
        PictureOfWord.setImageResource(picOfWord);
        NameOfWord.setText(nameOfWord);
        PronounOfWord.setText(pronounOfWord);
        DescripOfWord.setText(descripOfWord);
        NotesOfWord.setText(notesOfWord);
        RatingOfWord.setText(String.valueOf(ratingOfWord));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //SRC:https://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android
        //Send data to edit activity
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToEdit = new Intent(DetailsActivity.this, EditActivity.class);
                GoToEdit.putExtra("PicOfWord_Sent_From_Detail"       ,picOfWord);
                GoToEdit.putExtra("NameOfWord_Sent_From_Detail"      ,nameOfWord);
                GoToEdit.putExtra("PronounOfWord_Sent_From_Detail"   ,pronounOfWord);
                GoToEdit.putExtra("DescripOfWord_Sent_From_Detail"   ,descripOfWord);
                GoToEdit.putExtra("NotesOfWord_Sent_From_Detail"     ,notesOfWord);
                GoToEdit.putExtra("RatingOfWord_Sent_From_Detail"    ,ratingOfWord);
                GoToEdit.putExtra("PositionOfWord_Sent_From_Detail"  ,positionOfWord);
                //Receive Data With finished activity
                startActivityForResult(GoToEdit,BETWEEN_DETAIL_EDIT_REQ);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent receivedData)
    {
        super.onActivityResult(requestCode , resultCode , receivedData);
        if (requestCode == BETWEEN_DETAIL_EDIT_REQ){
            if (resultCode == BETWEEN_DETAIL_EDIT_RES)
            {
                Intent returner = receivedData;
                setResult(BETWEEN_LIST_DETAIL_RES,returner);
                finish();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(    "saved_pic"        ,picOfWord);
        outState.putString( "saved_name"       ,nameOfWord);
        outState.putString( "saved_pronoun"    ,pronounOfWord);
        outState.putString( "saved_descrip"    ,descripOfWord);
        outState.putString( "saved_notes"      ,notesOfWord);
        outState.putDouble( "saved_rating"     ,ratingOfWord);
        outState.putInt(    "saved_position"   ,positionOfWord);
    }
}
