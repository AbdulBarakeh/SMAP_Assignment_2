package android.abdul.wordLearner2.activities;

import android.abdul.wordLearner2.datamodels.WordTemplate;
import android.abdul.wordLearner2.service.WordLearnerService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.abdul.wordLearner2.R;

public class EditActivity extends AppCompatActivity {

private Button cancel;
private Button OK;
private TextView Name;
private EditText Notes;
private TextView Rating;
private SeekBar RatingBar;

static final int BETWEEN_DETAIL_EDIT_RES = 98;
WordTemplate word;
WordLearnerService wordService;

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
        OK = findViewById(R.id.ACTIVITY_EDIT_UPDATE_BUT);
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

    private Messenger msgQueue = null;//needed to send msg to service from activity
    private ComponentName cn = new ComponentName("EditActivity","EditActivity.class");//Gotta figure out what this is
    private IBinder binder = new Binder();//same for this

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name , IBinder service) {
            wordService = ( (WordLearnerService.WordleranerServiceBinder) service ).getService();
            word = wordService.getWord(Name.getText().toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void getDataFromService(){
        //Might be useless now taht we actually get our data from service onBind with getWord()
    }
    public void update(View v){
        wordService.updateWord(Name.getText().toString());
    }
}
