package android.abdul.wordLearner2.database;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// SRC inspiation: https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24
//SRC inspiration: https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-4-repository
//SRC: whole WordRespository.class file - Help from student ID:  TODO: MIKKEL ID

public class WordRepository {
public ExecutorService executor = Executors.newSingleThreadExecutor();
    private WordDatabase wordDatabase;
    public WordRepository(Context context) {
        wordDatabase = Room.databaseBuilder(context,WordDatabase.class,"WordDatabase17")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);//"Insert notes here" Is inserted as default note for the already populated words. Reason is because SQL can't implement an empty string. At least I haven't figured it out...
                        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                                " VALUES (\"https://media.owlbot.info/dictionary/images/kkkkkkw.jpg.400x400_q85_box-0,0,600,600_crop_detail.jpg\"," +
                                "\"Buffalo\",\"ˈbəf(ə)ˌlō\"," +
                                "\"a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.\"," +
                                "\"\",0.0)");
                        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                                " VALUES (\"https://media.owlbot.info/dictionary/images/nnnt.png.400x400_q85_box-0,0,500,500_crop_detail.png\"," +
                                "\"Camel\",\"ˈkaməl\"," +
                                "\"a large, long-necked ungulate mammal of arid country, with long slender legs, broad cushioned feet, and either one or two humps on the back. Camels can survive for long periods without food or drink, chiefly by using up the fat reserves in their humps.\"," +
                                "\"\",0.0)");
                        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                                " VALUES (\"https://media.owlbot.info/dictionary/images/sssssb.jpg.400x400_q85_box-0,0,500,500_crop_detail.jpg\"," +
                                "\"Cheetah\",\"ˈCHēdə\"," +
                                "\"a large slender spotted cat found in Africa and parts of Asia. It is the fastest animal on land.\"," +
                                "\"\",0.0)");
                        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                                " VALUES (\"https://media.owlbot.info/dictionary/images/rrrrrm.jpg.400x400_q85_box-0,0,500,500_crop_detail.jpg\"," +
                                "\"Crocodile\",\"ˈkräkəˌdīl\"," +
                                "\"a large predatory semiaquatic reptile with long jaws, long tail, short legs, and a horny textured skin.\"," +
                                "\"\",0.0)");
                        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                                " VALUES (\"https://media.owlbot.info/dictionary/images/27ti5gwrzr_Julie_Larsen_Maher_3242_African_Elephant_UGA_06_30_10_hr.jpg.400x400_q85_box-356,0,1156,798_crop_detail.jpg\"," +
                                "\"Elephant\",\"ˈeləfənt\"," +
                                "\"a very large plant-eating mammal with a prehensile trunk, long curved ivory tusks, and large ears, native to Africa and southern Asia. It is the largest living land animal.\"," +
                                "\"\",0.0)");
                    }
                })
                .build();
    }
    public List<WordEntity> getAllWords() throws ExecutionException, InterruptedException {

        Callable<List<WordEntity>> getwords = new Callable<List<WordEntity>>(){
            @Override
            public List<WordEntity> call() throws Exception {
                return wordDatabase.wordDao().getAllWords();
            }
        };
        return executor.submit(getwords).get();
    }
    public WordEntity findByName(final String name) throws ExecutionException, InterruptedException {

        Callable<WordEntity> getwordByname = new Callable<WordEntity>(){
            @Override
            public WordEntity call() throws Exception {
                return wordDatabase.wordDao().findByName(name);
            }
        };
        return executor.submit(getwordByname).get();
    }
    public WordEntity findByUid(final int uid) throws ExecutionException, InterruptedException {

        Callable<WordEntity> getwordByUid = new Callable<WordEntity>(){
            @Override
            public WordEntity call() throws Exception {
                return wordDatabase.wordDao().findByUid(uid);
            }
        };
        return executor.submit(getwordByUid).get();
    }
    //AsyncTasks Are deprecated and should not be used
    public void insertAll( final List<WordEntity> words){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDatabase.wordDao().insertAll(words);
                return null;
            }
        }.execute();
    }

    public void insertOne( final WordEntity word){

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                wordDatabase.wordDao().insertOne(word);
                return null;
            }
        }.execute();
    }
    public void delete( final WordEntity word) {

        new AsyncTask<WordEntity, Void, Void>() {
            @Override
            protected Void doInBackground(WordEntity... wordEntities) {
                wordDatabase.wordDao().delete(word._name);

                return null;
            }
        }.execute();
    }

    public void updateOne( final WordEntity word){
        new AsyncTask<WordEntity,Void,Void>(){

            @Override
            protected Void doInBackground(WordEntity... wordEntities) {
                wordDatabase.wordDao().updateOne(word);
                return null;
            }
        }.execute();
    }

}
