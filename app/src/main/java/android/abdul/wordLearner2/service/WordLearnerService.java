package android.abdul.wordLearner2.service;

import android.abdul.wordLearner2.API.API;
import android.abdul.wordLearner2.R;
import android.abdul.wordLearner2.activities.ListActivity;
import android.abdul.wordLearner2.database.WordEntity;
import android.abdul.wordLearner2.database.WordRepository;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static android.abdul.wordLearner2.BaseApplication.SERVICE_CHANNEL;
import static android.abdul.wordLearner2.BaseApplication.SUGGESTION_CHANNEL;


public class WordLearnerService extends Service {
    private static final String TAG = "WordLearnerService";
    ArrayList<WordEntity> wordList = new ArrayList<>();
    WordEntity word = new WordEntity();
    LocalBroadcastManager LBM;
    WordRepository DB;
    API api;
    Context context;
    Runnable run;
    Handler handler = new Handler();

    //Binder
    IBinder binder = new WordleranerServiceBinder();
    private boolean isRunning = true;

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
        context=this;
        //LINK RESOURCE: https://codinginflow.com/tutorials/android/notifications-notification-channels/part-1-notification-channels
        // Inspiration have been drawn from the other parts of the tutorial as well.
        Intent serviceIntent = new Intent(this, ListActivity.class);

        PendingIntent pendingIntent_service = PendingIntent.getActivity(this,0,serviceIntent,0);

        Notification service = new NotificationCompat.Builder(this, SERVICE_CHANNEL)
                .setChannelId(SERVICE_CHANNEL)
                .setOngoing(true)
                .setContentTitle("Wordlearner Service")
                .setContentText("Service is running...")
                .setContentIntent(pendingIntent_service)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_foxicon)
                .build();
        startForeground(666,service);

        LBM = LocalBroadcastManager.getInstance(this);
        DB = new WordRepository(getApplicationContext());
        api = new API();
        try {
            GetSamples();
        }
        catch ( ExecutionException e ) {
            e.printStackTrace();
        }
        catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        pushNotification();
        run.run();
    }
//LINK SOURCE: https://www.youtube.com/watch?v=QfQE1ayCzf8 "How to Start a Background Thread in Android"
    private void pushNotification() {
        final Random rand = new Random();
        run = new Runnable(){

            @Override
            public void run() {
                List<WordEntity> list = wordList;
                WordEntity randomElement = list.get(rand.nextInt(list.size()));
                Notification suggestion = new NotificationCompat.Builder(context, SUGGESTION_CHANNEL)
                        .setChannelId(SUGGESTION_CHANNEL)
                        .setContentTitle("Suggested word")
                        .setContentText("Learn this word: "+ randomElement.getName())
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSmallIcon(R.drawable.ic_foxicon)
                        .build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(667,suggestion);
                if (isRunning){
                    handler.postDelayed(this , 60000);
                }
            }
        };
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
        return binder;
    }
    @Override
    public void onDestroy() {
        Log.d(TAG , "onDestroy: I Destroyed");
        super.onDestroy();
        isRunning = false;
    }

    public ArrayList<WordEntity> getAllWords() throws ExecutionException, InterruptedException {
        //List is element based which makes it work better with databases, which is why we convert it to arraylist instead of making DB return Arraylist in the first place.
        List<WordEntity> temp = DB.getAllWords();
        wordList.clear();
        wordList.addAll(temp);
        return wordList;
    }

    public WordEntity getWord(String word){
        return findWordInList(word);
    }

    public void addWord(String word){
        WordEntity res;
        for (WordEntity specificWord : wordList){
            if (specificWord.getName().toLowerCase().equals(word.toLowerCase())){
                Log.d(TAG , "addWord: Word already exist");
                Toast.makeText(this , "Word already exist" , Toast.LENGTH_SHORT).show();
                return;
            }
        }
        api.parseJason(this,word);
    }
    public void deleteWord(String word) throws ExecutionException, InterruptedException {
        deleteWordFromList(word);
    }

    public void updateWord(WordEntity word){
        DB.update(word);
        update(word);
    }

//    Get list of words
    private void GetSamples() throws ExecutionException, InterruptedException {
        List<WordEntity> tempList = DB.getAllWords();
        wordList.addAll(tempList);
    }
    private WordEntity findWordInList(String word){
        for (WordEntity specificWord : wordList) {
            if (specificWord.getName().equals(word)){
                Log.d(TAG, "findWordInList: Word found");
                return specificWord;
            }
        }
        Log.d(TAG, "findWordInList: Word not found");
        return new WordEntity();
    }
    private void deleteWordFromList(String word) throws ExecutionException, InterruptedException {
        WordEntity wordTobeDeleted = DB.findByName(word);
        DB.delete(wordTobeDeleted);
        wordList.remove(wordTobeDeleted);
        updateDataset();
        Log.d(TAG , "deleteWordFromList: I'M HERE");

    }

    private void update(WordEntity word){
        Intent broadcaster = new Intent().setAction("delete");
        broadcaster.putExtra("word", word);
        LBM.sendBroadcast(broadcaster);
        Log.d(TAG , "update: Word updated");
    }

    private void updateDataset(){
        Intent broadcasterupdate = new Intent().setAction("update");
        Log.d(TAG , "updateDataset: Dataset updated");
        LBM.sendBroadcast(broadcasterupdate);
    }

    public void addApiWord(WordEntity parsedWord) {
        WordEntity newWord = new WordEntity();
        newWord = parsedWord;
        if (newWord.getImage() == null || newWord.getImage().equals("")){
            newWord.setImage("https://stockpictures.io/wp-content/uploads/2020/01/image-not-found-big-768x432.png");
        }
        DB.InsertOne(newWord);
        wordList.add(newWord);
        update(newWord);
        Log.d(TAG , "addApiWord: APIword Added to list");
    }

}
