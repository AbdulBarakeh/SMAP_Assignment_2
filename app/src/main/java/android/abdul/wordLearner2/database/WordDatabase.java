package android.abdul.wordLearner2.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// src: https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24
//SRC: https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-4-repository
@Database(entities = {WordEntity.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase instance;
    public abstract WordDao wordDao();
//    public static synchronized WordDatabase getInstance(Context context){
//        if (instance == null){
//            instance = Room.databaseBuilder(context.getApplicationContext(),WordDatabase.class,"word_database")
//                    .fallbackToDestructiveMigration()
//                    .addCallback(callback)
//                    .allowMainThreadQueries()
//                    .build();
//        }
//        return instance;
//    }
//    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new populateDBAsyncTask(instance).execute();
//        }
//    };
//    private static class populateDBAsyncTask extends AsyncTask<Void, Void, Void>{
//        private WordDao wordDao;
//        private populateDBAsyncTask(WordDatabase db){
//            wordDao = db.wordDao();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            wordDao.insertOne(new WordEntity("https://media.owlbot.info/dictionary/images/kkkkkkw.jpg.400x400_q85_box-0,0,600,600_crop_detail.jpg",     "Buffalo",  "ˈbəf(ə)ˌlō",   "a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
//            wordDao.insertOne(new WordEntity("https://media.owlbot.info/dictionary/images/nnnt.png.400x400_q85_box-0,0,500,500_crop_detail.png",       "Camel",    "ˈkaməl",       "a large, long-necked ungulate mammal of arid country, with long slender legs, broad cushioned feet, and either one or two humps on the back. Camels can survive for long periods without food or drink, chiefly by using up the fat reserves in their humps.","",0));
//            wordDao.insertOne(new WordEntity("https://media.owlbot.info/dictionary/images/sssssb.jpg.400x400_q85_box-0,0,500,500_crop_detail.jpg",     "Cheetah",  "ˈCHēdə",       "a large slender spotted cat found in Africa and parts of Asia. It is the fastest animal on land.","",0));
//            wordDao.insertOne(new WordEntity("https://media.owlbot.info/dictionary/images/rrrrrm.jpg.400x400_q85_box-0,0,500,500_crop_detail.jpg",   "Crocodile","ˈkräkəˌdīl",   "a large predatory semiaquatic reptile with long jaws, long tail, short legs, and a horny textured skin.","",0));
//            wordDao.insertOne(new WordEntity("https://media.owlbot.info/dictionary/images/27ti5gwrzr_Julie_Larsen_Maher_3242_African_Elephant_UGA_06_30_10_hr.jpg.400x400_q85_box-356,0,1156,798_crop_detail.jpg",    "Elephant", "ˈeləfənt",     "a very large plant-eating mammal with a prehensile trunk, long curved ivory tusks, and large ears, native to Africa and southern Asia. It is the largest living land animal.","",0));
//            return null;
//        }
//    }
}




