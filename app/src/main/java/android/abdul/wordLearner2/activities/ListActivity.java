package android.abdul.wordLearner2.activities;

import android.abdul.wordLearner2.AdapterForWordList;
import android.abdul.wordLearner2.R;
import android.abdul.wordLearner2.database.WordEntity;
import android.abdul.wordLearner2.service.WordLearnerService;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";
    public static final String BROADCAST = "android.abdul.wordLearner2.activities.ListActivity";
    public static final String BROADCAST_UPDATE = "android.abdul.wordLearner2.activities.ListActivity";
    private AdapterForWordList AdapterListActivity;
    RecyclerView recyclerViewListActivity;
    private EditText SearchInput;
    private Button addButton;

    ArrayList<WordEntity> WordList = new ArrayList<>();
    private WordLearnerService wordService;
    Intent wordServiceIntent;
    BroadcastListReceiver listReceiver;
    LocalBroadcastManager LBM;
    BroadcastUpdateReceiver updateReceiver;

//    BroadcastReceiver updateReceiver;
//    BroadcastReceiver deleteReceiver;

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
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReceiver);
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(deleteReceiver);
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

        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name , IBinder service) {
                wordService = ( (WordLearnerService.WordleranerServiceBinder) service ).getService();
                WordList = wordService.getAllWords();
                AdapterListActivity.updateList(WordList);
                AdapterListActivity.notifyDataSetChanged();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {}
        };
        private void registerBroadcast() {
            LBM = LocalBroadcastManager.getInstance(ListActivity.this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("delete");
            IntentFilter intentFilterupdate = new IntentFilter();
            intentFilterupdate.addAction("update");

            listReceiver = new BroadcastListReceiver();
            updateReceiver = new BroadcastUpdateReceiver();

            LBM.registerReceiver(listReceiver, intentFilter);
            LBM.registerReceiver(updateReceiver, intentFilterupdate);


        }
        public class  BroadcastListReceiver extends BroadcastReceiver{
            @Override
            public void onReceive(Context context , Intent intent) {
//                WordEntity sentWord = intent.getParcelableExtra("word");
//                for (WordEntity currentword : WordList) {
//                    if (currentword.getName().equals(sentWord.getName())){
//                        currentword.setRating(sentWord.getRating());
//                        currentword.setNotes(sentWord.getNotes());
//                        AdapterListActivity.notifyDataSetChanged();
//                    }
//                }
//                AdapterListActivity.notifyDataSetChanged();
                Log.d(TAG , "onReceive: DELETE!!!");
            }
        }
//
////        private void registerUpdateBroadcast() {
////            LUBM = LocalBroadcastManager.getInstance(ListActivity.this);
////            IntentFilter intentFilterupdate = new IntentFilter();
////            intentFilterupdate.addAction(BROADCAST_UPDATE);
////            updateReceiver = new BroadcastUpdateReceiver();
////            LUBM.registerReceiver(updateReceiver, intentFilterupdate);
////        }
        public class BroadcastUpdateReceiver extends BroadcastReceiver{
            @Override
            public void onReceive(Context context , Intent intent) {
                WordList = wordService.getAllWords();
                AdapterListActivity.updateList(WordList);
                AdapterListActivity.notifyDataSetChanged();
                Log.d(TAG , "onReceive: Wordlist updated in" + TAG);
            }
        }

}

