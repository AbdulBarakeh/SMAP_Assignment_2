package android.abdul.wordLearner2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
WordTemplate word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState != null){
            word = savedInstanceState.getParcelable("word");
        }
        else {
            Intent receivedFromList = getIntent();
            word = receivedFromList.getParcelableExtra("word");
        }
        //Bind UI elements with local variables
        Picture = findViewById( R.id.PictureOfWord_Detail);
        Name = findViewById( R.id.NameOfWord_Detail);
        Pronoun = findViewById( R.id.PronounOfWord_Detail);
        Descrip = findViewById( R.id.DescriptionOfWord_Detail);
        Notes = findViewById( R.id.NotesOfWord_Detail);
        Rating = findViewById( R.id.RatingOfWord_Detail);
        cancel = findViewById(R.id.ACTIVITY_DETAIL_CANCEL_BUTTON);
        edit = findViewById(R.id.ACTIVITY_DETAIL_BUTTON_EDIT);

        //Set the data into the UI elements
        Picture.setImageResource(word.getImage());
        Name.setText(word.getName());
        Pronoun.setText(word.getPronoun());
        Descrip.setText(word.getDescrip());
        Notes.setText(word.getNotes());
        Rating.setText(String.valueOf(word.getRating()));
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
        outState.putParcelable("word",word);
    }
    private Messenger msgQueue = null;//needed to send msg to service from activity
    private ComponentName cn = new ComponentName("DetailsActivity","DetailActivity.class");//Gotta figure out what this is
    private IBinder binder = new Binder();//same for this

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name , IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void getDataFromService(){
        serviceConnection.onServiceConnected(cn,binder);//NoClue It's all mock ups
        serviceConnection.onServiceDisconnected(cn);// maybe
    }
    public void delete(View v){

    }
}