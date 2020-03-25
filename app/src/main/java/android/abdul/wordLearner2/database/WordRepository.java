package android.abdul.wordLearner2.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordRepository {
    private String DB_NAME = "db_words";

    private WordDatabase wordDatabase;
    public WordRepository(Context context) {
        wordDatabase = Room.databaseBuilder(context, WordDatabase.class, DB_NAME).build();
    }
    public ArrayList<WordEntity> getAll(){
        final ArrayList<WordEntity> list = new ArrayList<WordEntity>();
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                list.addAll(wordDatabase.wordDao().getAll());
                return null;
            }
        }.execute();
        return list;
    }
    public ArrayList<WordEntity> loadAllbyUid(final int[] wordIds){
        final ArrayList<WordEntity> list = new ArrayList<WordEntity>();
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                list.addAll(wordDatabase.wordDao().loadAllByUids(wordIds));
                return null;
            }
        }.execute();
        return list;
    }
    public WordEntity findByName(final String name){
        final WordEntity[] word = new WordEntity[1];
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                word[0] = wordDatabase.wordDao().findByName(name);
                return null;
            }
        }.execute();
        return word[0];
    }
    public WordEntity findByUid(final int uid){
        final WordEntity[] word = new WordEntity[1];
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                word[0] = wordDatabase.wordDao().findByUid(uid);
                return null;
            }
        }.execute();
    return word[0];
    }
    public void insertAll(final ArrayList<WordEntity> words){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDatabase.wordDao().insertAll(words);
                return null;
            }
        }.execute();
    }
    public void insertOne(final WordEntity word){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDatabase.wordDao().insertOne(word);
                return null;
            }
        }.execute();
    }
    public void delete(final WordEntity word){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDatabase.wordDao().delete(word);
                return null;
            }
        }.execute();

    }
    public void updateOne(final WordEntity word){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                wordDatabase.wordDao().updateOne(word);
                return null;
            }
        }.execute();
    }
}
