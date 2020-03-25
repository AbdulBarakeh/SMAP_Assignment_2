package android.abdul.wordLearner2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface WordDao {
    @Query("SELECT * FROM WordEntity")
    List<WordEntity> getAll();

    @Query("SELECT * FROM WordEntity WHERE _uid IN (:wordUids)")
    List<WordEntity> loadAllByUids(int[] wordUids);

    @Query("SELECT * FROM WordEntity WHERE name LIKE :name LIMIT 1")
    WordEntity findByName(String name);

    @Query("SELECT * FROM WordEntity WHERE _uid LIKE :uid LIMIT 1")
    WordEntity findByUid(int uid);

    @Insert
    void insertAll(ArrayList<WordEntity> words);

    @Delete
    void delete(WordEntity word);

    @Insert
    void insertOne(WordEntity word);

    @Update
    void updateOne(WordEntity word);

}
