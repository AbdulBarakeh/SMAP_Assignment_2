package au590917.abdul.wordLearner2.adapter;

import au590917.abdul.wordLearner2.R;
import au590917.abdul.wordLearner2.database.WordEntity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import au590917.abdul.wordLearner2.database.WordEntity;

// Over all I got no effing clue about what this file does. I just know that it is needed to make the recyclerview work
// SRC: https://codinginflow.com/tutorials/android/simple-recyclerview-java/part-1-layouts-model-class part 1-5
public class AdapterForWordList extends RecyclerView.Adapter<AdapterForWordList.ViewHolderForWordList> {
    private ArrayList<WordEntity> ListOfWords;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private OnItemClickListener CardClickListener;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    //Setting up clicklistener for every card
    public void setOnItemClickListener(OnItemClickListener listener){
        CardClickListener = listener;
    }
    //Extending viewholder class for our specific need
    public static class ViewHolderForWordList extends RecyclerView.ViewHolder{
        public NetworkImageView Picture;
        public TextView Name;
        public TextView Pronoun;
        public TextView Rating;

        public ViewHolderForWordList(View itemView, final OnItemClickListener listener) {
            super(itemView);
            Picture = (NetworkImageView)itemView.findViewById(R.id.PicOfWord_CardInfo);
            Name = itemView.findViewById(R.id.NameOfWord_CardInfo);
            Pronoun = itemView.findViewById(R.id.PronounOfWord_CardInfo);
            Rating = itemView.findViewById(R.id.RatingOfWord_CardInfo);
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
    public void updateList(ArrayList<WordEntity> UpdatedWordList){ListOfWords = UpdatedWordList;}
    public AdapterForWordList(Context context, ArrayList<WordEntity> WordList){
        ListOfWords = WordList;
        this.context = context;
        loadImage();
    }
    @Override
    public ViewHolderForWordList onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        ViewHolderForWordList VH = new ViewHolderForWordList(V, CardClickListener );
        return VH;
    }
    @Override
    public void onBindViewHolder(ViewHolderForWordList holder, int position) {
        WordEntity currentItem = ListOfWords.get(position);

        holder.Picture.setImageUrl(currentItem.getImage(),mImageLoader);
        holder.Name.setText(currentItem.getName());
        holder.Pronoun.setText(currentItem.getPronounciation());
        holder.Rating.setText(String.valueOf(currentItem.getRating()));
    }
    @Override
    public int getItemCount() {
        return ListOfWords.size();
    }

// SRC:   https://cypressnorth.com/mobile-application-development/setting-android-google-volley-imageloader-networkimageview/
    private void loadImage() {
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(20);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }
}
