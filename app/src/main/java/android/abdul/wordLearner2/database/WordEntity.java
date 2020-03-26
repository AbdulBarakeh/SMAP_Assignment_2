package android.abdul.wordLearner2.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

@Entity
public class WordEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int _uid;

    @ColumnInfo(name = "image")
    int _image;

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

    public WordEntity(int _Image, String _Name, String _Pronounciation, String _Description, String _Notes, double _Rating){
        _image              = _Image;
        _name             = _Name;
        _pronounciation    = _Pronounciation;
        _description      = _Description;
        _notes           = _Notes;
        _rating           = _Rating;
    }
    public WordEntity(){
        _image          = 0;
        _name           = "";
        _pronounciation = "";
        _description    = "";
        _notes          = "";
        _rating         = 0;
    }




    public int getUid() {
        return _uid;
    }

    public void setUid(int uid) {
        this._uid = uid;
    }

    public int getImage() {
        return _image;
    }

    public void setImage(int _image) {
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


    public static final Creator<WordEntity> CREATOR = new Creator<WordEntity>() {
        @Override
        public WordEntity createFromParcel(Parcel in) {
            return new WordEntity(in);
        }

        @Override
        public WordEntity[] newArray(int size) {
            return new WordEntity[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest , int flags) {
        dest.writeInt(_image);
        dest.writeString(_name);
        dest.writeString(_pronounciation);
        dest.writeDouble(_rating);
        dest.writeString(_description);
        dest.writeString(_notes);
    }
    protected WordEntity(Parcel in) {
        _image = in.readInt();
        _name = in.readString();
        _pronounciation = in.readString();
        _rating = in.readDouble();
        _description = in.readString();
        _notes = in.readString();
    }
}
