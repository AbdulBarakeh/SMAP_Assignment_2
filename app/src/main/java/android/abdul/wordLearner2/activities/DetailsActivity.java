package android.abdul.wordLearner2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.abdul.wordLearner2.database.WordEntity;
import android.abdul.wordLearner2.datamodels.WordTemplate;
import android.abdul.wordLearner2.service.WordLearnerService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.abdul.wordLearner2.R;

public class DetailsActivity extends AppCompatActivity {

private Button cancel;
private Button edit;
private ImageView Picture;
private TextView Name;
private TextView Pronoun;
private TextView Descrip;
private TextView Notes;
private TextView Rating;

static final int BETWEEN_LIST_DETAIL_RES = 99;
static final int BETWEEN_DETAIL_EDIT_RES = 98;
static final int BETWEEN_DETAIL_EDIT_REQ = 102;
    WordEntity word;
WordLearnerService wordService;
String wordname;
Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent receivedFromList = getIntent();
        word = receivedFromList.getParcelableExtra("word");
        initializeUi();
        setViewdata();

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
                GoToEdit.putExtra("word",word);
                startActivity(GoToEdit);
                finish();

            }
        });
    }

    private void setViewdata() {
        //Set the data into the UI elements
        Picture.setImageResource(word.getImage());
        Name.setText(word.getName());
        Pronoun.setText(word.getPronounciation());
        Descrip.setText(word.getDescription());
        Notes.setText(word.getNotes());
        Rating.setText(String.valueOf(word.getRating()));
    }

    private void initializeUi() {
        //Bind UI elements with local variables
        Picture = findViewById( R.id.PictureOfWord_Detail);
        Name = findViewById( R.id.NameOfWord_Detail);
        Pronoun = findViewById( R.id.PronounOfWord_Detail);
        Descrip = findViewById( R.id.DescriptionOfWord_Detail);
        Notes = findViewById( R.id.NotesOfWord_Detail);
        Rating = findViewById( R.id.RatingOfWord_Detail);
        cancel = findViewById(R.id.ACTIVITY_DETAIL_CANCEL_BUTTON);
        edit = findViewById(R.id.ACTIVITY_DETAIL_BUTTON_EDIT);
    }
//    private void startMyService() {
//        intent = new Intent(this, WordLearnerService.class);
//        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
//    }
//    ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name , IBinder service) {
//            wordService = ((WordLearnerService.WordleranerServiceBinder) service ).getService();
//            word = wordService.getWord(wordname);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            // nothing
//        }
//    };

    public void delete(View v){
        wordService.deleteWord(Name.getText().toString());
    }
}