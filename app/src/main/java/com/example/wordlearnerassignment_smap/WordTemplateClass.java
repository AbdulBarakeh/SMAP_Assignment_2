package com.example.wordlearnerassignment_smap;

public class WordTemplateClass {

    private int ImageOfWord;
    private String NameOfWord;
    private String PronounOfWord;
    private double RatingOfWord;
    private String DescripOfWord;
    private String NotesOfWord;
    WordTemplateClass(int _ImageOfWord, String _NameOfWord, String _PronounOfWord, String _DescripOfWord, String _NotesOfWord, double _RatingOfWord){
        ImageOfWord = _ImageOfWord;
        NameOfWord = _NameOfWord;
        PronounOfWord = _PronounOfWord;
        DescripOfWord = _DescripOfWord;
        NotesOfWord = _NotesOfWord;
        RatingOfWord = _RatingOfWord;
    }

    public int getImageOfWord() {
        return ImageOfWord;
    }
    public String getNameOfWord() {
        return NameOfWord;
    }
    public String getPronounOfWord() {
        return PronounOfWord;
    }
    public double getRatingOfWord(){ return RatingOfWord;}
    public String getDescripOfWord() {
        return DescripOfWord;
    }
    public String getNotesOfWord() {
        return NotesOfWord;
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
}
