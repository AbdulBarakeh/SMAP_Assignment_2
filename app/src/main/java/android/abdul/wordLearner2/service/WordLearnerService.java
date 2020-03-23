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
import android.widget.Switch;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import static android.abdul.wordLearner2.BaseApplication.SERVICE_CHANNEL;
import static android.abdul.wordLearner2.BaseApplication.SUGGESTION_CHANNEL;

public class WordLearnerService extends Service {
    private static final String TAG = "WordLearnerService";
    //Message msgQueue = new Message();

    public class WordleranerServiceBinder extends Binder{
        public WordLearnerService getService(){
            return WordLearnerService.this;
        }
    }

    public WordLearnerService() {
    }
    @Override
    public void onCreate() {
        Log.d(TAG , "onCreate: I Exist");
        Intent suggestionIntent = new Intent(this, ListActivity.class);
        Intent serviceIntent = new Intent(this, ListActivity.class);

        PendingIntent pendingIntent_suggestion = PendingIntent.getActivity(this,0,suggestionIntent,0);
        PendingIntent pendingIntent_service = PendingIntent.getActivity(this,0,serviceIntent,0);

        Notification service = new NotificationCompat.Builder(this, SERVICE_CHANNEL)
                .setChannelId(SERVICE_CHANNEL)
                .setOngoing(true)
                .setContentTitle("Wordlearner Service")
                .setContentText("Service is running...")
                .setContentIntent(pendingIntent_service)
                .setSmallIcon(R.drawable.foxicon)
                .build();

        Notification suggestion = new NotificationCompat.Builder(this, SUGGESTION_CHANNEL)
                .setChannelId(SUGGESTION_CHANNEL)
                .setOngoing(true)
                .setContentTitle("Suggested word")
                .setContentText("*INSERT WORD*")
                .setContentIntent(pendingIntent_suggestion)
                .setSmallIcon(R.drawable.ic_and)
                .build();

        startForeground(666,service);
        startForeground(667,suggestion);

    }

    @Override
    public int onStartCommand(Intent intent , int flags , int startId) {
        super.onStartCommand(intent ,flags , startId);
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG , "onBind: I binded");
        return new WordleranerServiceBinder();
    }
    @Override
    public void onDestroy() {
        Log.d(TAG , "onDestroy: I Destroyed");
        super.onDestroy();
    }

    public ArrayList<WordTemplate> getAllWords(){

        return new ArrayList<WordTemplate>();
    }

    public WordTemplate getWord(String word){
        return new WordTemplate();
    }

    public WordTemplate addWord(String word){
        //search database first
        //If no result
        //search API
        //Insert in wordtemplate
        return new WordTemplate();
    }
    public void deleteWord(String word){

    }
    public void updateWord(String word){

    }

}
