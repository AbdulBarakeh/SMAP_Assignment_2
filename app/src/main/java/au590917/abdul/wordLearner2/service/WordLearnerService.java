package au590917.abdul.wordLearner2.service;
import au590917.abdul.wordLearner2.API.API;
import au590917.abdul.wordLearner2.R;
import au590917.abdul.wordLearner2.activities.ListActivity;
import au590917.abdul.wordLearner2.database.WordEntity;
import au590917.abdul.wordLearner2.database.WordRepository;
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
import static au590917.abdul.wordLearner2.BaseApp.BaseApplication.SERVICE_CHANNEL;
import static au590917.abdul.wordLearner2.BaseApp.BaseApplication.SUGGESTION_CHANNEL;

public class WordLearnerService extends Service {
    private static final String TAG = "WordLearnerService";
    private static final int SERVICE_NOTIFICATION = 18;
    private static final int PUSH_NOTIFICATION = 20;
    private ArrayList<WordEntity> wordList = new ArrayList<>();
    private LocalBroadcastManager LBM;
    private WordRepository DB;
    private API api;
    private Context context;
    private Runnable run;
    private Handler handler = new Handler();
    private IBinder binder = new WordleranerServiceBinder();

    @Override
    public void onCreate() {
        context=this;
        //LINK RESOURCE: https://codinginflow.com/tutorials/android/notifications-notification-channels/part-1-notification-channels
        // Inspiration have been drawn from the other parts of the tutorial as well.
        Intent serviceIntent = new Intent(this, ListActivity.class);
        PendingIntent pendingIntent_service = PendingIntent.getActivity(this,0,serviceIntent,0);
        Notification service = new NotificationCompat.Builder(this, SERVICE_CHANNEL)
                .setChannelId(SERVICE_CHANNEL)
                .setOngoing(true)
                .setContentTitle(getString(R.string.wordlearnerServiceTitle))
                .setContentText(getString(R.string.wordlearnerserviceText))
                .setContentIntent(pendingIntent_service)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_foxicon)
                .build();
        startForeground(SERVICE_NOTIFICATION,service);
        Initializer();
        try {
            GetSamples();
        }
        catch ( ExecutionException e ) {
            e.printStackTrace();
        }
        catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        //declares runnable(new thread) and runs it
        pushNotification();
        run.run();
        Log.d(TAG , "onCreate: I Exist");
    }

    private void Initializer() {
        LBM = LocalBroadcastManager.getInstance(this);
        DB = new WordRepository(getApplicationContext());
        api = new API();
    }

    //LINK SOURCE: https://www.youtube.com/watch?v=QfQE1ayCzf8 "How to Start a Background Thread in Android"
    // Pushnotification gets upadted every minute until onDestroy() 
    private void pushNotification() {
        final Random rand = new Random();
        run = new Runnable(){

            @Override
            public void run() {
            List<WordEntity> list = wordList;
            if (list.size() != 0) {
                WordEntity randomElement = list.get(rand.nextInt(list.size()));
                Notification suggestion = new NotificationCompat.Builder(context , SUGGESTION_CHANNEL)
                        .setChannelId(SUGGESTION_CHANNEL)
                        .setContentTitle(getString(R.string.FreeThignsTitle))
                        .setContentText(getString(R.string.FreeThingsText) + randomElement.getName())
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSmallIcon(R.drawable.ic_foxicon)
                        .build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(PUSH_NOTIFICATION , suggestion);
            }
                    handler.postDelayed(this , 60000);
            }
        };
    }

    //===GET METHODS===//
    private void GetSamples() throws ExecutionException, InterruptedException {
        List<WordEntity> tempList = DB.getAllWords();
        wordList.addAll(tempList);
    }
    public ArrayList<WordEntity> getAllWords() throws ExecutionException, InterruptedException {
        //List is element based which makes it work better with databases, which is why we convert it to arraylist instead of making DB return Arraylist in the first place.
        List<WordEntity> temp = DB.getAllWords();
        wordList.clear();
        wordList.addAll(temp);
        Log.d(TAG , "getAllWords: All words gotten");
        return wordList;
    }
    public WordEntity getWord(String word){
        Log.d(TAG , "getWord: " + word + "is being found");
        return findWordInList(word);
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

    //===DELETE METHODS===//
    public void deleteWord(String word) throws ExecutionException, InterruptedException {
        Log.d(TAG , "deleteWord: " + word + "is being deleted...");
        deleteWordFromList(word);
    }
    private void deleteWordFromList(String word) throws ExecutionException, InterruptedException {
        WordEntity wordTobeDeleted = DB.findByName(word);
        DB.delete(wordTobeDeleted);
        wordList.remove(wordTobeDeleted);
        updateDataset();
        Log.d(TAG , "deleteWordFromList: word deleted");
        Toast.makeText(context , word+ getString(R.string.deletedWord_LIST_ACTIVITY) , Toast.LENGTH_SHORT).show();

    }

    //===UPDATE METHODS===//
    public void updateWord(WordEntity word) {
        DB.update(word);
        update(word);
    }
    // Inspiration from -> SRC: https://www.techotopia.com/index.php/Broadcast_Intents_and_Broadcast_Receivers_in_Android_Studio
    private void update(WordEntity word){
        Intent broadcaster = new Intent().setAction("update_word");
        broadcaster.putExtra("word", word);
        LBM.sendBroadcast(broadcaster);
        Log.d(TAG , "update: Word updated");
        Toast.makeText(context , ""+word.getName()+getString(R.string.updateWord_LIST_ACTIVITY) , Toast.LENGTH_SHORT).show();
    }
    // Inspiration from -> SRC: https://www.techotopia.com/index.php/Broadcast_Intents_and_Broadcast_Receivers_in_Android_Studio
    private void updateDataset(){
        Intent broadcasterupdate = new Intent().setAction("update_dataset");
        Log.d(TAG , "updateDataset: Dataset updated");
        LBM.sendBroadcast(broadcasterupdate);
    }

    //===ADD METHODS===//
    public void addWord(String word){
        for (WordEntity specificWord : wordList){
            if (specificWord.getName().toLowerCase().equals(word.toLowerCase())){
                Log.d(TAG , "addWord: "+ specificWord.getName() +" already exist");
                Toast.makeText(this , ""+specificWord.getName()+getString(R.string.addWordToast_WORD_EXIST) , Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Log.d(TAG , "addWord: Looking for word in API...");
        api.parseJason(this,word);
        Toast.makeText(context , "" + word + context.getString(R.string.WordAdded_LIST_ACTIVITY) , Toast.LENGTH_SHORT).show();
    }
    public void addApiWord(WordEntity parsedWord) throws ExecutionException, InterruptedException {
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

    //===EXTENSIONS AND OVERRIDES===//
    //Binder Singleton. To get the same instance every time.
    public class WordleranerServiceBinder extends Binder{
        public WordLearnerService getService(){
            return WordLearnerService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG , "onBind: I binded");
        return binder;
    }
    //===DESTROY===//
    @Override
    public void onDestroy() {
        Log.d(TAG , "onDestroy: I Destroyed");
        super.onDestroy();
    }
}