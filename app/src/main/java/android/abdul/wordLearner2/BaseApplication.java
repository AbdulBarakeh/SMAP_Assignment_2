package android.abdul.wordLearner2;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

public class BaseApplication extends Application {
    public static final String SUGGESTION_CHANNEL = "Suggestion_Channel";
    public static final String SERVICE_CHANNEL = "Service_Channel";
    private static final String TAG = "BaseApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

            NotificationChannel serviceChannel = new NotificationChannel(SERVICE_CHANNEL,"Service_Channel", NotificationManager.IMPORTANCE_DEFAULT);
            serviceChannel.setDescription("Starts service");

            NotificationChannel suggestionChannel = new NotificationChannel(SUGGESTION_CHANNEL,"Suggestion_Channel", NotificationManager.IMPORTANCE_DEFAULT);
            suggestionChannel.setDescription("Creates suggestions for words");

            NotificationManager notificationManagerService = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManagerService.createNotificationChannel(serviceChannel);
            NotificationManager notificationManagerSuggestion = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManagerSuggestion.createNotificationChannel(suggestionChannel);
        }
        Log.d(TAG , "createNotificationChannel: I*M OOOO ");
    }
}
