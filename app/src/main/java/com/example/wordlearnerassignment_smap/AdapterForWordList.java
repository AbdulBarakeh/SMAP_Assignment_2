package com.example.wordlearnerassignment_smap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
// Over all I got no effing clue about what this file does. I just know that it is needed to make the recyclerview work
// SRC: https://codinginflow.com/tutorials/android/simple-recyclerview-java/part-1-layouts-model-class part 1-5
public class AdapterForWordList extends RecyclerView.Adapter<AdapterForWordList.ViewHolderForWordList> {
    private ArrayList<WordTemplate> ListOfWords;

    private OnItemClickListener CardClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    //Setting up clicklistener for every card
    public void setOnItemClickListener(OnItemClickListener listener){
        CardClickListener = listener;
    }
    //Extending viewholder class for our specific need
    public static class ViewHolderForWordList extends RecyclerView.ViewHolder{
        public ImageView PictureOfWord;
        public TextView NameOfWord;
        public TextView PronounOfWord;
        public TextView RatingOfWord;

        public ViewHolderForWordList(View itemView, final OnItemClickListener listener) {
            super(itemView);
            PictureOfWord = itemView.findViewById(R.id.PicOfWord_CardInfo);
            NameOfWord = itemView.findViewById(R.id.NameOfWord_CardInfo);
            PronounOfWord = itemView.findViewById(R.id.PronounOfWord_CardInfo);
            RatingOfWord = itemView.findViewById(R.id.RatingOfWord_CardInfo);
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            } );
        }
    }

    public AdapterForWordList(ArrayList<WordTemplate> WordList){
        ListOfWords = WordList;
    }
    @Override
    public ViewHolderForWordList onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        ViewHolderForWordList VH = new ViewHolderForWordList(V, CardClickListener );
        return VH;
    }

    @Override
    public void onBindViewHolder(ViewHolderForWordList holder, int position) {
        WordTemplate currentItem = ListOfWords.get(position);

        holder.PictureOfWord.setImageResource(currentItem.getImageOfWord());
        holder.NameOfWord.setText(currentItem.getNameOfWord());
        holder.PronounOfWord.setText(currentItem.getPronounOfWord());
        holder.RatingOfWord.setText(String.valueOf(currentItem.getRatingOfWord()));
    }
    @Override
    public int getItemCount() {
        return ListOfWords.size();
    }
}
