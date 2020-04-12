package au590917.abdul.wordLearner2.activities;

import au590917.abdul.wordLearner2.R;
import au590917.abdul.wordLearner2.database.WordEntity;
import au590917.abdul.wordLearner2.service.WordLearnerService;
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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import java.util.concurrent.ExecutionException;

public class DetailsActivity extends AppCompatActivity {

    private Button cancel, edit, delete;
    private NetworkImageView Picture;
    private TextView Name, Pronoun, Descrip, Notes, Rating;
    private static final int BETWEEN_DETAIL_EDIT_REQ = 102;
    private WordEntity word;
    private WordLearnerService wordService;
    private String wordName;
    private Intent intent;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent receivedFromList = getIntent();
        wordName = receivedFromList.getStringExtra("word");
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
                GoToEdit.putExtra("word", wordName);
                startActivityForResult(GoToEdit,BETWEEN_DETAIL_EDIT_REQ);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    wordService.deleteWord(word.getName());
                }
                catch ( ExecutionException e ) {
                    e.printStackTrace();
                }
                catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
                finish();
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
    // SRC:   https://cypressnorth.com/mobile-application-development/setting-android-google-volley-imageloader-networkimageview/
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
        delete = findViewById(R.id.ACTIVITY_DETAIL_BUTTON_DELETE);
    }
    private void startMyService() {
        intent = new Intent(this, WordLearnerService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
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