package android.abdul.wordLearner2.service;

import android.abdul.wordLearner2.datamodels.WordTemplate;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WordLearnerService extends Service {
    public WordLearnerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent , int flags , int startId) {
        return super.onStartCommand(intent , flags , startId);
    }

    @Override
    public void onDestroy() {
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
