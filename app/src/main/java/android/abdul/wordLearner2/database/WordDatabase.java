package android.abdul.wordLearner2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
// src: https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24
@Database(entities = {WordEntity.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    public abstract WordDao wordDao();
}
