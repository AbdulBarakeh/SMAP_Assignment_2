package au590917.abdul.wordLearner2.activities;

import au590917.abdul.wordLearner2.adapter.AdapterForWordList;
import au590917.abdul.wordLearner2.R;
import au590917.abdul.wordLearner2.database.WordEntity;
import au590917.abdul.wordLearner2.service.WordLearnerService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import static au590917.abdul.wordLearner2.service.WordLearnerService.UPDATE_DATASET;
import static au590917.abdul.wordLearner2.service.WordLearnerService.UPDATE_WORD;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";
    private static AdapterForWordList AdapterListActivity;
    private RecyclerView recyclerViewListActivity;
    private EditText SearchInput;
    private Button addButton;
    private static ArrayList<WordEntity> WordList = new ArrayList<>();
    private static WordLearnerService wordService;
    private Intent wordServiceIntent;
    private BroadcastListReceiver listReceiver;
    private LocalBroadcastManager LBM;
    private BroadcastUpdateReceiver updateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setupRecyclerview();
        Button exit = findViewById(R.id.Exit_button_List);
        SearchInput = findViewById(R.id.search_Input);
        addButton = findViewById(R.id.add_button_list);
        //Send data to next activity
        AdapterListActivity.setOnItemClickListener( new AdapterForWordList.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            Intent GoToDetails = new Intent(ListActivity.this, DetailsActivity.class);
            GoToDetails.putExtra("word",WordList.get(position).getName());
            startActivity(GoToDetails);
            }
        } );

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = SearchInput.getText().toString();
                wordService.addWord(word);
                AdapterListActivity.notifyDataSetChanged();
            }
        });
        startMyService();
        registerBroadcast();
    }

    private void startMyService() {
        wordServiceIntent = new Intent(this, WordLearnerService.class);
        ContextCompat.startForegroundService(this, wordServiceIntent);
        bindService(wordServiceIntent,serviceConnection,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        LBM.unregisterReceiver(listReceiver);
        LBM.unregisterReceiver(updateReceiver);
    }

    private void setupRecyclerview() {
        //Setup recyclerview and attach layout and adapter + EXIT button
        recyclerViewListActivity = findViewById(R.id.recyclerView);
        recyclerViewListActivity.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerListActivity = new LinearLayoutManager(this);
        AdapterListActivity = new AdapterForWordList(this,WordList);
        recyclerViewListActivity.setLayoutManager(layoutManagerListActivity);
        recyclerViewListActivity.setAdapter(AdapterListActivity);
        AdapterListActivity.notifyDataSetChanged();
    }
        // SRC: https://developer.android.com/guide/components/bound-services
        //Bind your service & Henrik ze teacher
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name , IBinder service) {
                wordService = ( (WordLearnerService.WordleranerServiceBinder) service ).getService();
                try {
                    WordList = wordService.getAllWords();
                }
                catch ( ExecutionException e ) {
                    e.printStackTrace();
                }
                catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
                AdapterListActivity.updateList(WordList);
                AdapterListActivity.notifyDataSetChanged();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {}
        };

        //register the different broadcasts
        // Inspiration from -> SRC: https://www.techotopia.com/index.php/Broadcast_Intents_and_Broadcast_Receivers_in_Android_Studio
        private void registerBroadcast() {
            LBM = LocalBroadcastManager.getInstance(ListActivity.this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(UPDATE_WORD);
            IntentFilter intentFilterupdate = new IntentFilter();
            intentFilterupdate.addAction(UPDATE_DATASET);

            listReceiver = new BroadcastListReceiver();
            updateReceiver = new BroadcastUpdateReceiver();

            LBM.registerReceiver(listReceiver, intentFilter);
            LBM.registerReceiver(updateReceiver, intentFilterupdate);
        }
        // Inspiration from -> SRC: https://www.techotopia.com/index.php/Broadcast_Intents_and_Broadcast_Receivers_in_Android_Studio
        //receives word update
        // updates on word update from edit activity
        public static class  BroadcastListReceiver extends BroadcastReceiver{
            @Override
            public void onReceive(Context context , Intent intent) {
                WordEntity sentWord = intent.getParcelableExtra("word");
                for (WordEntity currentword : WordList) {
                    if (currentword.getName().equals(sentWord.getName())){
                        currentword.setRating(sentWord.getRating());
                        currentword.setNotes(sentWord.getNotes());
                        AdapterListActivity.notifyDataSetChanged();
                    }
                }
                Log.d(TAG , "onReceive: "+sentWord.getName()+" updated");
            }
        }
        //Inspiration from -> SRC: https://www.techotopia.com/index.php/Broadcast_Intents_and_Broadcast_Receivers_in_Android_Studio
        // receives dataset update.
        // Update whole list instead of specific word
        //Updates on delete from detail activity
        public static class BroadcastUpdateReceiver extends BroadcastReceiver{
            @Override
            public void onReceive(Context context , Intent intent) {
                try {
                    WordList = wordService.getAllWords();
                }
                catch ( ExecutionException e ) {
                    e.printStackTrace();
                }
                catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
                AdapterListActivity.updateList(WordList);
                AdapterListActivity.notifyDataSetChanged();
                Log.d(TAG , "onReceive: Wordlist updated in" + TAG);
            }
        }

}

