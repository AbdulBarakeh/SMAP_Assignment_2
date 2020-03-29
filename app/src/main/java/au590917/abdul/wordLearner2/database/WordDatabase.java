package au590917.abdul.wordLearner2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// src: https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24
//SRC: https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-4-repository
@Database(entities = {WordEntity.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase instance;
    public abstract WordDao wordDao();
}




