package com.example.wordlearnerassignment_smap;

import android.os.Parcel;
import android.os.Parcelable;
//SRC: https://stackoverflow.com/questions/31490657/how-to-use-onsaveinstancestate-method-with-arraylist
//Creation of Wordclass
public class WordTemplate implements Parcelable {

    private int ImageOfWord;
    private String NameOfWord;
    private String PronounOfWord;
    private double RatingOfWord;
    private String DescripOfWord;
    private String NotesOfWord;
    private int PositionOfWord;

    WordTemplate(int _ImageOfWord, String _NameOfWord, String _PronounOfWord, String _DescripOfWord, String _NotesOfWord, double _RatingOfWord, int _PositionOfWord){
        ImageOfWord = _ImageOfWord;
        NameOfWord = _NameOfWord;
        PronounOfWord = _PronounOfWord;
        DescripOfWord = _DescripOfWord;
        NotesOfWord = _NotesOfWord;
        RatingOfWord = _RatingOfWord;
        PositionOfWord = _PositionOfWord;
    }

    protected WordTemplate(Parcel in) {
        ImageOfWord = in.readInt();
        NameOfWord = in.readString();
        PronounOfWord = in.readString();
        RatingOfWord = in.readDouble();
        DescripOfWord = in.readString();
        NotesOfWord = in.readString();
        PositionOfWord = in.readInt();
    }

    public int getImageOfWord() {
        return        ImageOfWord   ;
    }
    public String getNameOfWord() {
        return      NameOfWord    ;
    }
    public String getPronounOfWord() {
        return   PronounOfWord ;
    }
    public double getRatingOfWord(){ return     RatingOfWord  ; }
    public String getDescripOfWord() {
        return   DescripOfWord ;
    }
    public String getNotesOfWord() {
        return     NotesOfWord   ;
    }
    public int getPositionOfWord() {
        return     PositionOfWord;
    }

    public void setImageOfWord(int imageOfWord) {
        ImageOfWord = imageOfWord;
    }

    public void setNameOfWord(String nameOfWord) {
        NameOfWord = nameOfWord;
    }

    public void setPronounOfWord(String pronounOfWord) {
        PronounOfWord = pronounOfWord;
    }

    public void setRatingOfWord(double ratingOfWord) {
        RatingOfWord = ratingOfWord;
    }

    public void setDescripOfWord(String descripOfWord) {
            DescripOfWord = descripOfWord;
    }

    public void setNotesOfWord(String notesOfWord) {
            NotesOfWord = notesOfWord;
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
        dest.writeInt(      ImageOfWord     );
        dest.writeString(   NameOfWord      );
        dest.writeString(   PronounOfWord   );
        dest.writeDouble(   RatingOfWord    );
        dest.writeString(   DescripOfWord   );
        dest.writeString(   NotesOfWord     );
        dest.writeInt(      PositionOfWord  );
    }
}
