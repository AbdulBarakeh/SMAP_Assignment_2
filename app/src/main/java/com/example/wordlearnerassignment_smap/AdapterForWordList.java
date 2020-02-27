package com.example.wordlearnerassignment_smap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForWordList extends RecyclerView.Adapter<AdapterForWordList.ViewHolderForWordList> {
    private ArrayList<WordTemplateClass> ListOfWords;

    private OnItemClickListener CardClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        CardClickListener = listener;

    }

    public static class ViewHolderForWordList extends RecyclerView.ViewHolder{
        public ImageView PictureOfWord_Adapter;
        public TextView NameOfWord_Adapter;
        public TextView PronounOfWord_Adapter;
        public TextView RatingOfWord_Adapter;
        public TextView DescripOfWord_Adapter;
        public TextView NotesOfWord_Adapter;

        public ViewHolderForWordList(View itemView, final OnItemClickListener listener) {
            super(itemView);
            PictureOfWord_Adapter = itemView.findViewById(R.id.PicOfWord_CardInfo);
            NameOfWord_Adapter = itemView.findViewById(R.id.NameOfWord_CardInfo);
            PronounOfWord_Adapter = itemView.findViewById(R.id.PronounOfWord_CardInfo);
            RatingOfWord_Adapter = itemView.findViewById(R.id.RatingOfWord_CardInfo);
            DescripOfWord_Adapter = itemView.findViewById(R.id.DescriptionOfWord_Detail);//Not sure if it will crash cause it doesn't have a UI element
            NotesOfWord_Adapter = itemView.findViewById(R.id.NotesOfWord_Detail);//NotSure same reason as above
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
    public AdapterForWordList(ArrayList<WordTemplateClass> WordList){
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
        WordTemplateClass currentItem = ListOfWords.get(position);

        holder.PictureOfWord_Adapter.setImageResource(currentItem.getImageOfWord());
        holder.NameOfWord_Adapter.setText(currentItem.getNameOfWord());
        holder.PronounOfWord_Adapter.setText(currentItem.getPronounOfWord());
        holder.RatingOfWord_Adapter.setText(String.valueOf(currentItem.getRatingOfWord()));
//        holder.NotesOfWord_Adapter.setText(currentItem.getNotesOfWord()); //Makes App Crash if enabled, no UI element to place the data
//        holder.DescripOfWord_Adapter.setText(currentItem.getDescripOfWord()); //Makes App Crash if enabled, no UI element to place the data
    }

    @Override
    public int getItemCount() {
        return ListOfWords.size();
    }
}
