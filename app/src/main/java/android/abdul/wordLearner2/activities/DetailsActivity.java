package android.abdul.wordLearner2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.abdul.wordLearner2.AdapterForWordList;
import android.abdul.wordLearner2.database.WordEntity;
import android.abdul.wordLearner2.datamodels.WordTemplate;
import android.abdul.wordLearner2.service.WordLearnerService;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.abdul.wordLearner2.R;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

private Button cancel;
private Button edit;
private NetworkImageView Picture;
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
private RequestQueue mRequestQueue;
private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent receivedFromList = getIntent();
        wordname = receivedFromList.getStringExtra("word");

        initializeUi();
        loadImage();


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
                GoToEdit.putExtra("word",wordname);
                startActivityForResult(GoToEdit,BETWEEN_DETAIL_EDIT_REQ);
//                finish();
            }
        });
        startMyService();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent receivedData)
    {
        super.onActivityResult(requestCode , resultCode , receivedData);
        if (requestCode == BETWEEN_DETAIL_EDIT_REQ){
            if (resultCode == Activity.RESULT_OK)
            {
                finish();
            }
        }
    }
    private void loadImage() {
        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }
    private void setViewdata() {
        //Set the data into the UI elements
        Picture.setImageUrl(word.getImage(),mImageLoader);
        Name.setText(word.getName());
        Pronoun.setText(word.getPronounciation());
        Descrip.setText(word.getDescription());
        Notes.setText(word.getNotes());
        Rating.setText(String.valueOf(word.getRating()));
    }

    private void initializeUi() {
        //Bind UI elements with local variables
        Picture = (NetworkImageView) findViewById( R.id.PictureOfWord_Detail);
        Name = findViewById( R.id.NameOfWord_Detail);
        Pronoun = findViewById( R.id.PronounOfWord_Detail);
        Descrip = findViewById( R.id.DescriptionOfWord_Detail);
        Notes = findViewById( R.id.NotesOfWord_Detail);
        Rating = findViewById( R.id.RatingOfWord_Detail);
        cancel = findViewById(R.id.ACTIVITY_DETAIL_CANCEL_BUTTON);
        edit = findViewById(R.id.ACTIVITY_DETAIL_BUTTON_EDIT);
    }
    private void startMyService() {
        intent = new Intent(this, WordLearnerService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name , IBinder service) {
            wordService = ( (WordLearnerService.WordleranerServiceBinder) service ).getService();
            word = wordService.getWord(wordname);
            setViewdata();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    public void delete(View v){
        wordService.deleteWord(word.getName());
        finish();
    }
}