package android.abdul.wordLearner2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;
//SRC: https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24
@Dao
public interface WordDao {
    @Query("SELECT * FROM Wordentity")
    List<WordEntity> getAllWords();

//    @Query("SELECT * FROM wordEntity WHERE uid IN (:wordUids)")
//    List<WordEntity> loadAllByUids(int[] wordUids);

    @Query("SELECT * FROM WordEntity WHERE name LIKE (:name) LIMIT 1")
    WordEntity findByName(String name);

    @Query("SELECT * FROM WordEntity WHERE uid in (:uid) LIMIT 1")
    WordEntity findByUid(int uid);

    @Insert
    void insertAll(List<WordEntity> words);

    @Insert
    void insertOne(WordEntity word);

    @Query("DELETE FROM Wordentity WHERE name = :word")
    void delete(String word);

    @Update
    void updateOne(WordEntity word);


}
