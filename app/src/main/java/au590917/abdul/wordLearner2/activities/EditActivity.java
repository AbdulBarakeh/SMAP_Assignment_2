package au590917.abdul.wordLearner2.activities;

import au590917.abdul.wordLearner2.R;
import au590917.abdul.wordLearner2.database.WordEntity;
import au590917.abdul.wordLearner2.service.WordLearnerService;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static au590917.abdul.wordLearner2.activities.DetailsActivity.DETAIL_TO_EDIT_KEY;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";
    private Button cancel, update;
    private TextView Name, Rating;
    private EditText Notes;
    private SeekBar RatingBar;
    private String wordName, savedNote;
    private WordEntity word;
    private WordLearnerService wordService;
    private Intent EditToDetail;
    private double savedRating;
    private Bundle savedState;
    private static String SAVED_WORD ="savedWord";
    private static String SAVED_NOTES = "notes";
    private static String SAVED_RATING = "rating";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        savedState = savedInstanceState;
        //Get all data either from detail or from the savedinstance.
        Intent receivedFromDetail = getIntent();
        if (savedState != null){
            word = savedInstanceState.getParcelable(SAVED_WORD);
            wordName = word.getName();
            savedNote = savedInstanceState.getString(SAVED_NOTES);
            savedRating = savedInstanceState.getDouble(SAVED_RATING,6.9);
        }
        else{
            wordName = receivedFromDetail.getStringExtra(DETAIL_TO_EDIT_KEY);
        }
        InitializeUI();
        EditToDetail = new Intent(this,DetailsActivity.class);

        //Send data back to detail activity on click
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.setNotes(Notes.getText().toString());
                word.setRating(Double.parseDouble(Rating.getText().toString()));
                wordService.updateWord(word);
                setResult(Activity.RESULT_OK);
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
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        startMyService();
    }

    private void setViewdata() {
        //Set the data into the UI elements
        Name.setText(word.getName());
        if (savedState == null) {
            Notes.setText(word.getNotes());
            Rating.setText(String.valueOf(word.getRating()));
            // Scalability of the trigger position, and placing in to corespond with the current rating.
            RatingBar.setMax(100);
            RatingBar.setProgress((int)(word.getRating()*10));
        }
        else{
            Notes.setText(savedNote);
            Rating.setText(String.valueOf(savedRating/10));
            Log.d(TAG , "setViewdata: saved data"+ Rating.getText());
            // Scalability of the trigger position, and placing in to corespond with the current rating.
            RatingBar.setMax(100);
            RatingBar.setProgress(((int)savedRating));
        }
    }

    private void startMyService() {
        Intent intent = new Intent(this, WordLearnerService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    private void InitializeUI() {
        //Bind UI elements with local variables
        cancel = findViewById(R.id.ACTIVITY_EDIT_CANCEL_BUTTON2);
        update = findViewById(R.id.ACTIVITY_EDIT_UPDATE_BUT);
        Name = findViewById( R.id.NameOfWord_Edit);
        Notes = findViewById( R.id.NotesInput_Edit);
        Rating = findViewById( R.id.Rating_Edit);
        RatingBar = findViewById(R.id.Seekbar_Edit);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_WORD,word);
        outState.putString(SAVED_NOTES,Notes.getText().toString());
        outState.putDouble(SAVED_RATING, Double.parseDouble(Rating.getText().toString())*10);
    }
    // SRC: https://developer.android.com/guide/components/bound-services
    //Bind your service & Henrik ze teacher
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name , IBinder service) {
            wordService = ( (WordLearnerService.WordleranerServiceBinder) service ).getService();
            word = wordService.getWord(wordName);
            setViewdata();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
