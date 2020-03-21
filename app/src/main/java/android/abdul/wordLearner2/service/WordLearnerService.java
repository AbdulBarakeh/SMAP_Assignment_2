package android.abdul.wordLearner2.service;

import android.abdul.wordLearner2.R;
import android.abdul.wordLearner2.activities.ListActivity;
import android.abdul.wordLearner2.datamodels.WordTemplate;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.abdul.wordLearner2.BaseApplication.SUGGESTION_CHANNEL;

public class WordLearnerService extends Service {
    private static final String TAG = "WordLearnerService";
    Message msgQueue = new Message();
    IBinder binder = new Binder();
    public WordLearnerService() {
    }
    @Override
    public void onCreate() {
        Log.d(TAG , "onCreate: I Exist");
        Intent obj = new Intent(this, ListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,obj,0);
        Notification suggestion = new NotificationCompat.Builder(this,SUGGESTION_CHANNEL)
                .setChannelId(SUGGESTION_CHANNEL)
                .setOngoing(true)
                .setContentTitle("SHIT")
                .setContentText("SHUT")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_and)
                .build();

        startForeground(667,suggestion);
    }

    @Override
    public int onStartCommand(Intent intent , int flags , int startId) {
       return super.onStartCommand(intent , flags , startId);

    }
    @Override
    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
   Log.d(TAG , "onBind: I binded");
//        binder = intent.getParcelableExtra("Binder");
//        return binder;
////        throw new UnsupportedOperationException("Not yet implemented");
 return null;
    }
    @Override
    public void onDestroy() {
        Log.d(TAG , "onDestroy: I Destroyed");
        super.onDestroy();
    }

    public void getAllWords(){

    }
    public WordTemplate addWord(String word){
        //search database first
        //If no result
        //search API
        //Insert in wordtemplate
        return new WordTemplate();
    }
    public WordTemplate getWord(String word){
        return new WordTemplate();
    }

    public void deleteWord(String word){

    }
    public void updateWord(String word){

    }
}
