package au590917.abdul.wordLearner2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
//SRC: https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24
@Dao
public interface WordDao {
    @Query("SELECT * FROM WordEntity")
    List<WordEntity> getAllWords();

    @Query("SELECT * FROM WordEntity WHERE name LIKE (:name) LIMIT 1")
    WordEntity findByName(String name);

    @Query("DELETE FROM WordEntity WHERE name = :word")
    void delete(String word);

    @Query("UPDATE WordEntity SET notes= :notes, rating= :rating WHERE name = :name COLLATE NOCASE" )
    void updateOne(String notes, double rating, String name);

    @Insert
    void insertAll(List<WordEntity> words);

    @Insert
    void insertOne(WordEntity word);






}
