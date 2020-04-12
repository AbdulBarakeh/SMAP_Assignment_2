package au590917.abdul.wordLearner2.BaseApp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import androidx.annotation.NonNull;
import au590917.abdul.wordLearner2.R;


//LINK RESOURCE: https://codinginflow.com/tutorials/android/notifications-notification-channels/part-1-notification-channels
// Inspiration have been drawn from the other parts of the tutorial as well.
//The functionality of this file is to create the notification channels which will be used in the app
//By extending the Application class createNotificationChannel() will be called on startup before anything else
public class BaseApplication extends Application {
    public static final String SUGGESTION_CHANNEL = "Suggestion_Channel";
    public static final String SERVICE_CHANNEL = "Service_Channel";
    private static final String TAG = "BaseApplication";
    NotificationManager notificationManager;
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    //Creating notification channelse for API versions over android OREO
    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

            NotificationChannel serviceChannel = new NotificationChannel(SERVICE_CHANNEL,"Service_Channel", NotificationManager.IMPORTANCE_HIGH);
            serviceChannel.setDescription(getString(R.string.ServiceDescripption));

            NotificationChannel suggestionChannel = new NotificationChannel(SUGGESTION_CHANNEL,"Suggestion_Channel", NotificationManager.IMPORTANCE_HIGH);
            suggestionChannel.setDescription(getString(R.string.SuggestionDescription));

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(serviceChannel);
            notificationManager.createNotificationChannel(suggestionChannel);
        }
        Log.d(TAG , "createNotificationChannel: notification channels created ");
    }
}
