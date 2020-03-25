package android.abdul.wordLearner2.datamodels;

import android.abdul.wordLearner2.activities.ListActivity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
//SRC: https://stackoverflow.com/questions/31490657/how-to-use-onsaveinstancestate-method-with-arraylist
//Creation of Wordclass
public class WordTemplate implements Parcelable {

    private int Image;
    private String Name;
    private String Pronoun;
    private double Rating;
    private String Descrip;
    private String Notes;
    private int Position;
    //I'm lazy so I made a constructor that doesn't take any arguments. It's nice to have while coding.
    public WordTemplate(){
        Image = new Integer(0);
        Name = new String();
        Pronoun = new String();
        Descrip = new String();
        Notes = new String();
        Rating = new Double(0);
        Position = new Integer(0);
    }
    public WordTemplate(int _Image, String _Name, String _Pronoun, String _Descrip, String _Notes, double _Rating, int _Position){
        Image = _Image;
        Name = _Name;
        Pronoun = _Pronoun;
        Descrip = _Descrip;
        Notes = _Notes;
        Rating = _Rating;
        Position = _Position;
    }

    protected WordTemplate(Parcel in) {
        Image = in.readInt();
        Name = in.readString();
        Pronoun = in.readString();
        Rating = in.readDouble();
        Descrip = in.readString();
        Notes = in.readString();
        Position = in.readInt();
    }

    public int getImage() {
        return Image;
    }
    public String getName() {
        return Name;
    }
    public String getPronounciation() {
        return Pronoun;
    }
    public double getRating(){ return Rating; }
    public String getDescription() {
        return Descrip;
    }
    public String getNotes() {
        return Notes;
    }
    public int getPosition() {
        return Position;
    }

    public void setImage(int image) {
        Image = image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPronoun(String pronoun) {
        Pronoun = pronoun;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public void setDescrip(String descrip) {
            Descrip = descrip;
    }

    public void setNotes(String notes) {
            Notes = notes;
    }

    //Implementation of Parcel Methods
    public static final Creator<WordTemplate> CREATOR = new Creator<WordTemplate>() {
        @Override
        public WordTemplate createFromParcel(Parcel in) {
            return new WordTemplate(in);
        }

        @Override
        public WordTemplate[] newArray(int size) {
            return new WordTemplate[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest , int flags) {
        dest.writeInt(Image);
        dest.writeString(Name);
        dest.writeString(Pronoun);
        dest.writeDouble(Rating);
        dest.writeString(Descrip);
        dest.writeString(Notes);
        dest.writeInt(Position);
    }
}
