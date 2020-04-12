package au590917.abdul.wordLearner2.database;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// SRC inspiation: https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24
//SRC inspiration: https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-4-repository
//SRC: whole WordRespository.class file - Help from student ID: AU547760
// Worked with student AU219980 so some resemblances might occur
//Interface between app and DB. Asynchronous calls, to the DAO which calls the DB, is made here
public class WordRepository {
public ExecutorService executor = Executors.newSingleThreadExecutor();
    private WordDatabase wordDatabase;
    public WordRepository(Context context) {
        wordDatabase = Room.databaseBuilder(context,WordDatabase.class,"WordDatabase51")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        PopulateDB(db);
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

    public void InsertOne(final WordEntity word){
    Runnable insertOne = new Runnable() {
        @Override
        public void run() {
            wordDatabase.wordDao().insertOne(word);
        }
    };
    executor.execute(insertOne);
    }
    public void delete(final WordEntity word){
        Runnable delete = new Runnable() {
            @Override
            public void run() {
                wordDatabase.wordDao().delete(word.getName());
            }
        };
        executor.execute(delete);
    }

    public void update(final WordEntity word){
        Runnable update = new Runnable() {
            @Override
            public void run() {
                wordDatabase.wordDao().updateOne(word.getNotes(), word.getRating(), word.getName());
            }
        };
        executor.execute(update);
    }

    private void PopulateDB(@NonNull SupportSQLiteDatabase db) {
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
        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                " VALUES (\"https://media.owlbot.info/dictionary/images/nnnk.jpg.400x400_q85_box-0,0,225,225_crop_detail.jpg\"," +
                "\"Giraffe\",\"jəˈraf\"," +
                "\"a large African mammal with a very long neck and forelegs, having a coat patterned with brown patches separated by lighter lines. It is the tallest living animal.\"," +
                "\"\",0.0)");
        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                " VALUES (\"https://media.owlbot.info/dictionary/images/qqqqql.jpg.400x400_q85_box-0,0,1413,1414_crop_detail.jpg\"," +
                "\"Gnu\",\"n(y)o͞o\"," +
                "\"a large dark antelope with a long head, a beard and mane, and a sloping back.\"," +
                "\"\",0.0)");
        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                " VALUES (\"https://media.owlbot.info/dictionary/images/kkkkkkj.jpg.400x400_q85_box-0,0,500,500_crop_detail.jpg\"," +
                "\"Kudu\",\"ˈko͞odo͞o\"," +
                "\"an African antelope that has a greyish or brownish coat with white vertical stripes, and a short bushy tail. The male has long spirally curved horns.\"," +
                "\"\",0.0)");
        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                " VALUES (\"https://media.owlbot.info/dictionary/images/ooooow.jpg.400x400_q85_box-23,22,478,477_crop_detail.jpg\"," +
                "\"Lion\",\"ˈlīən\"," +
                "\"a large tawny-coloured cat that lives in prides, found in Africa and NW India. The male has a flowing shaggy mane and takes little part in hunting, which is done cooperatively by the females.\"," +
                "\"\",0.0)");
        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                " VALUES (\"https://media.owlbot.info/dictionary/images/oooooz.jpg.400x400_q85_box-0,0,500,500_crop_detail.jpg\"," +
                "\"Leopard\",\"ˈlepərd\"," +
                "\"a large solitary cat that has a fawn or brown coat with black spots, native to the forests of Africa and southern Asia.\"," +
                "\"\",0.0)");
        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                " VALUES (\"https://media.owlbot.info/dictionary/images/nnnnnn.jpg.400x400_q85_box-0,0,500,500_crop_detail.jpg\"," +
                "\"Oryx\",null," +
                "\"a large antelope living in arid regions of Africa and Arabia, having dark markings on the face and long horns.\"," +
                "\"\",0.0)");
        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                " VALUES (\"https://media.owlbot.info/dictionary/images/gggk.jpg.400x400_q85_box-0,0,225,225_crop_detail.jpg\"," +
                "\"Ostrich\",\"ˈästriCH\"," +
                "\"a flightless swift-running African bird with a long neck, long legs, and two toes on each foot. It is the largest living bird, with males reaching a height of up to 2.75 m.\"," +
                "\"\",0.0)");
        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                " VALUES (\"https://media.owlbot.info/dictionary/images/dn.jpg.400x400_q85_box-576,0,1226,649_crop_detail.jpg\"," +
                "\"Shark\",\"SHärk\"," +
                "\"a long-bodied chiefly marine fish with a cartilaginous skeleton, a prominent dorsal fin, and tooth-like scales. Most sharks are predatory, though the largest kinds feed on plankton, and some can grow to a large size.\"," +
                "\"\",0.0)");
        db.execSQL("INSERT INTO WordEntity(image,name,pronunciation,description,notes,rating)" +
                " VALUES (\"https://media.owlbot.info/dictionary/images/llllllg.jpg.400x400_q85_box-0,0,225,225_crop_detail.jpg\"," +
                "\"Snake\",\"snāk\"," +
                "\"a long limbless reptile which has no eyelids, a short tail, and jaws that are capable of considerable extension. Some snakes have a venomous bite.\"," +
                "\"\",0.0)");
    }
}
