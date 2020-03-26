package android.abdul.wordLearner2.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int _uid;

    @ColumnInfo(name = "image")
    String _image;

    @ColumnInfo(name = "image_int")
    int _image_int;

    @ColumnInfo(name = "name")
    String _name;

    @ColumnInfo(name = "pronounciation")
    String _pronounciation;

    @ColumnInfo(name = "description")
    String _description;

    @ColumnInfo(name = "rating")
    double _rating;

    @ColumnInfo(name = "notes")
    String _notes;


    public WordEntity(String _Image, int _image_int, String _Name, String _Pronounciation, String _Description, String _Notes, double _Rating){
        _image              = _Image;
        this._image_int          = _image_int;
        _name             = _Name;
        _pronounciation    = _Pronounciation;
        _description      = _Description;
        _notes           = _Notes;
        _rating           = _Rating;
    }
    public WordEntity(String _Image, String _Name, String _Pronounciation, String _Description, String _Notes, double _Rating){
        _image              = _Image;
        _name             = _Name;
        _pronounciation    = _Pronounciation;
        _description      = _Description;
        _notes           = _Notes;
        _rating           = _Rating;
    }
    public WordEntity(int _image_int, String _Name, String _Pronounciation, String _Description, String _Notes, double _Rating){
        this._image_int          = _image_int;
        _name             = _Name;
        _pronounciation    = _Pronounciation;
        _description      = _Description;
        _notes           = _Notes;
        _rating           = _Rating;
    }

    public WordEntity(){
        _image          = "";
        _image_int      =  0;
        _name           = "";
        _pronounciation = "";
        _description    = "";
        _notes          = "";
        _rating         = 0;
    }

    public int get_image_int() {
        return _image_int;
    }

    public void set_image_int(int _image_int) {
        this._image_int = _image_int;
    }

    public int getUid() {
        return _uid;
    }

    public void setUid(int uid) {
        this._uid = uid;
    }

    public String getImage() {
        return _image;
    }

    public void setImage(String _image) {
        this._image = _image;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getPronounciation() {
        return _pronounciation;
    }

    public void setPronounciation(String _pronounciation) {
        this._pronounciation = _pronounciation;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }

    public double getRating() {
        return _rating;
    }

    public void setRating(double rating) {
        this._rating = rating;
    }

    public String getNotes() {
        return _notes;
    }

    public void setNotes(String notes) {
        this._notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest , int flags) {

    }
}
