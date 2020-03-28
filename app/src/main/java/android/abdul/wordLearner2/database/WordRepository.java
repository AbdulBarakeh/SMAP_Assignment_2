package android.abdul.wordLearner2.database;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import androidx.room.Room;
import java.util.ArrayList;
import java.util.List;
// SRC: https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24
//SRC: https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-4-repository
public class WordRepository {
    private WordDao wordDao;
    private List<WordEntity> allWords;
    public WordRepository(Application application) {
        WordDatabase wordDatabase = WordDatabase.getInstance(application);
        wordDao = wordDatabase.wordDao();
        allWords = wordDao.getAll();
    }
    public void insertAll( ArrayList<WordEntity> words){
        new InsertAllWordsAsyncTask(wordDao).execute(words);
    }
    public void insertOne( WordEntity word){
        new InsertWordAsyncTask(wordDao).execute(word);
    }
    public void delete( WordEntity word){
        new RemovetWordAsyncTask(wordDao).execute(word);
    }
    public void updateOne( WordEntity word){
        new UpdateWordAsyncTask(wordDao).execute(word);
    }

    private static class InsertWordAsyncTask extends AsyncTask<WordEntity, Void,Void>{
        private WordDao wordDao;

        private InsertWordAsyncTask(WordDao wordDao){
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordEntity... wordEntities) {
            wordDao.insertOne(wordEntities[0]);
            return null;
        }
    }

    private static class RemovetWordAsyncTask extends AsyncTask<WordEntity, Void,Void>{
        private WordDao wordDao;

        private RemovetWordAsyncTask(WordDao wordDao){
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordEntity... wordEntities) {
            wordDao.delete(wordEntities[0]);
            return null;
        }
    }

    private static class InsertAllWordsAsyncTask extends AsyncTask<ArrayList<WordEntity>, Void,Void>{
        private WordDao wordDao;

        private InsertAllWordsAsyncTask(WordDao wordDao){
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(ArrayList<WordEntity>... wordEntities) {
            wordDao.insertAll(wordEntities[0]);
            return null;
        }
    }
    private static class UpdateWordAsyncTask extends AsyncTask<WordEntity, Void,Void>{
        private WordDao wordDao;

        private UpdateWordAsyncTask(WordDao wordDao){
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordEntity... wordEntities) {
            wordDao.updateOne(wordEntities[0]);
            return null;
        }
    }
}
