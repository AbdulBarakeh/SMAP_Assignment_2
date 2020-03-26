package android.abdul.wordLearner2.service;

import android.abdul.wordLearner2.R;
import android.abdul.wordLearner2.activities.ListActivity;
import android.abdul.wordLearner2.database.WordDatabase;
import android.abdul.wordLearner2.database.WordEntity;
import android.abdul.wordLearner2.database.WordRepository;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Room;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.abdul.wordLearner2.BaseApplication.SERVICE_CHANNEL;
import static android.abdul.wordLearner2.BaseApplication.SUGGESTION_CHANNEL;
import static com.android.volley.Request.*;
import static com.android.volley.Request.Method.GET;


public class WordLearnerService extends Service {
    private static final String TAG = "WordLearnerService";
    ArrayList<WordEntity> wordList = new ArrayList<>();
    WordEntity word = new WordEntity();
    LocalBroadcastManager LBM;
    //Binder
    IBinder binder = new WordleranerServiceBinder();



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
        CreateSamples();
        LBM = LocalBroadcastManager.getInstance(this);

        WordRepository DB = new WordRepository(getApplicationContext());

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
    }

    public ArrayList<WordEntity> getAllWords(){
        return wordList;
    }

    public WordEntity getWord(String word){
        return findWordInList(word);
    }

    public void addWord(String word){
        //search database first
        //If no result
        //search API
        //Insert in wordtemplate

        WordEntity newWord = new WordEntity();
        newWord.setName(word);
        newWord.setImage("R.drawable.imagenotfound");
        wordList.add(newWord);
    }
    public void deleteWord(String word){
        deleteWordFromList(word);
    }
    public void updateWord(WordEntity word){
        update(word);
    }

    //Create list of words
    private ArrayList<WordEntity> CreateSamples(){
        ArrayList<WordEntity> Sample = new ArrayList<>();
        Sample.add(new WordEntity("https://media.owlbot.info/dictionary/images/kkkkkkw.jpg.400x400_q85_box-0,0,600,600_crop_detail.jpg",     "Buffalo",  "ˈbəf(ə)ˌlō",   "a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordEntity("https://media.owlbot.info/dictionary/images/nnnt.png.400x400_q85_box-0,0,500,500_crop_detail.png",       "Camel",    "ˈkaməl",       "a large, long-necked ungulate mammal of arid country, with long slender legs, broad cushioned feet, and either one or two humps on the back. Camels can survive for long periods without food or drink, chiefly by using up the fat reserves in their humps.","",0));
        Sample.add(new WordEntity("https://media.owlbot.info/dictionary/images/sssssb.jpg.400x400_q85_box-0,0,500,500_crop_detail.jpg",     "Cheetah",  "ˈCHēdə",       "a large slender spotted cat found in Africa and parts of Asia. It is the fastest animal on land.","",0));
        Sample.add(new WordEntity("https://media.owlbot.info/dictionary/images/rrrrrm.jpg.400x400_q85_box-0,0,500,500_crop_detail.jpg",   "Crocodile","ˈkräkəˌdīl",   "a large predatory semiaquatic reptile with long jaws, long tail, short legs, and a horny textured skin.","",0));
        Sample.add(new WordEntity("https://media.owlbot.info/dictionary/images/27ti5gwrzr_Julie_Larsen_Maher_3242_African_Elephant_UGA_06_30_10_hr.jpg.400x400_q85_box-356,0,1156,798_crop_detail.jpg",    "Elephant", "ˈeləfənt",     "a very large plant-eating mammal with a prehensile trunk, long curved ivory tusks, and large ears, native to Africa and southern Asia. It is the largest living land animal.","",0));
//        Sample.add(new WordEntity(R.drawable.giraffe,     "Giraffe",  "jəˈraf",       "a large African mammal with a very long neck and forelegs, having a coat patterned with brown patches separated by lighter lines. It is the tallest living animal.","",0));
//        Sample.add(new WordEntity(R.drawable.gnu,         "Gnu",      "n(y)o͞o",       "a large dark antelope with a long head, a beard and mane, and a sloping back.","",0));
//        Sample.add(new WordEntity(R.drawable.kudo,        "Kudo",     "ˈko͞odo͞o",      "an African antelope that has a greyish or brownish coat with white vertical stripes, and a short bushy tail. The male has long spirally curved horns.","",0));
//        Sample.add(new WordEntity(R.drawable.leopard,     "Leopard",  "ˈlepərd",      "a large solitary cat that has a fawn or brown coat with black spots, native to the forests of Africa and southern Asia.","",0));
//        Sample.add(new WordEntity(R.drawable.lion,        "Lion",     "ˈlīən",        "a large tawny-coloured cat that lives in prides, found in Africa and NW India. The male has a flowing shaggy mane and takes little part in hunting, which is done cooperatively by the females.","",0));
//        Sample.add(new WordEntity(R.drawable.oryx,        "Oryx",     "null",         "a large antelope living in arid regions of Africa and Arabia, having dark markings on the face and long horns.","",0));
//        Sample.add(new WordEntity(R.drawable.ostrich,     "Ostrich",  "ˈästriCH",     "a flightless swift-running African bird with a long neck, long legs, and two toes on each foot. It is the largest living bird, with males reaching a height of up to 2.75 m.","",0));
//        Sample.add(new WordEntity(R.drawable.shark,       "Shark",    "SHärk",        "a long-bodied chiefly marine fish with a cartilaginous skeleton, a prominent dorsal fin, and tooth-like scales. Most sharks are predatory, though the largest kinds feed on plankton, and some can grow to a large size.","",0));
//        Sample.add(new WordEntity(R.drawable.snake,       "Snake",    "snāk",         "a long limbless reptile which has no eyelids, a short tail, and jaws that are capable of considerable extension. Some snakes have a venomous bite.","",0));
        wordList = Sample;
        return wordList;
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
    private void deleteWordFromList(String word){
        for (WordEntity currentListWord : wordList ) {
            if (currentListWord.getName().equals(word)){
                Log.d(TAG, "deleteWordFromList: Word removed");
                wordList.remove(currentListWord);
            }
        }
    }

    private void update(WordEntity word){
        Intent broadcaster = new Intent().setAction(ListActivity.BROADCAST);
        broadcaster.putExtra("word", word);
        LBM.sendBroadcast(broadcaster);
    }

    public void addApiWord(WordEntity parsedWord) {
        wordList.add(parsedWord);
    }

}
