package android.abdul.wordLearner2;

import android.abdul.wordLearner2.database.WordDatabase;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.room.Room;

//LINK RESOURCE: https://codinginflow.com/tutorials/android/notifications-notification-channels/part-1-notification-channels
// Inspiration have been drawn from the other parts of the tutorial as well.
public class BaseApplication extends Application {
    public static final String SUGGESTION_CHANNEL = "Suggestion_Channel";
    public static final String SERVICE_CHANNEL = "Service_Channel";
    private static final String TAG = "BaseApplication";
    private WordDatabase db;
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
//        getTaskDatabase();
    }
    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

            NotificationChannel serviceChannel = new NotificationChannel(SERVICE_CHANNEL,"Service_Channel", NotificationManager.IMPORTANCE_HIGH);
            serviceChannel.setDescription("Starts service");

            NotificationChannel suggestionChannel = new NotificationChannel(SUGGESTION_CHANNEL,"Suggestion_Channel", NotificationManager.IMPORTANCE_HIGH);
            suggestionChannel.setDescription("Creates suggestions for words");

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(serviceChannel);
            notificationManager.createNotificationChannel(suggestionChannel);
        }
        Log.d(TAG , "createNotificationChannel: notification channels created ");
    }
//    //SRC: TheSituationRoom1.6 Demo ITSMAPF20
//    //singleton pattern used, for lazy loading + single instance since db object is expensive
//    public WordDatabase getTaskDatabase(){
//        if(db == null){
//            //this builder is for simplicity of the example and not good practise
//            //- dangerous to allow queries on the main thread as it could block
//            //- destructive migrations is dangerous as you might loose data with change in schema
//            db = Room.databaseBuilder(this, WordDatabase.class, "my_words").allowMainThreadQueries().fallbackToDestructiveMigration().addCallback(db.callback).build();
//        }
//        return db;
//    }
}
