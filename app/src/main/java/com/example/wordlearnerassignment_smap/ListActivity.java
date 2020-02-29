package com.example.wordlearnerassignment_smap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private RecyclerView RecyclerViewListActivity;
    private AdapterForWordList AdapterListActivity;
    private RecyclerView.LayoutManager LayoutManagerListActivity;
    private Button Exit;
    ArrayList<WordTemplate> WordList = new ArrayList<>();
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
//SRC: https://stackoverflow.com/questions/31490657/how-to-use-onsaveinstancestate-method-with-arraylist
        if (savedInstanceState!= null){
            WordList = savedInstanceState.getParcelableArrayList("list_activity");
        }
        else{
            WordList = CreateSamples();
        }


        RecyclerViewListActivity = findViewById(R.id.recyclerView);
        RecyclerViewListActivity.setHasFixedSize(true);
        LayoutManagerListActivity = new LinearLayoutManager(this);
        AdapterListActivity = new AdapterForWordList(WordList);
        RecyclerViewListActivity.setLayoutManager(LayoutManagerListActivity);
        RecyclerViewListActivity.setAdapter(AdapterListActivity);
        AdapterListActivity.setOnItemClickListener( new AdapterForWordList.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent GoToDetails = new Intent(ListActivity.this, DetailsActivity.class);
                GoToDetails.putExtra("PicOfWord", WordList.get(position).getImageOfWord());
                GoToDetails.putExtra("NameOfWord", WordList.get(position).getNameOfWord());
                GoToDetails.putExtra("PronounOfWord", WordList.get(position).getPronounOfWord());
                GoToDetails.putExtra("DescripOfWord", WordList.get(position).getDescripOfWord());
                GoToDetails.putExtra("NotesOfWord", WordList.get(position).getNotesOfWord());
                GoToDetails.putExtra("RatingOfWord", WordList.get(position).getRatingOfWord());
                currentPosition = position;
                startActivityForResult(GoToDetails,99);
            }
        } );
        Exit = findViewById(R.id.Exit_button_List);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
//Handle Received Data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent receivedIntent) {
        super.onActivityResult(requestCode , resultCode , receivedIntent);
            if (resultCode == 99) {
                WordList.get(currentPosition).setImageOfWord(receivedIntent.getIntExtra("pic" , R.drawable.imagenotfound));
                WordList.get(currentPosition).setNameOfWord(receivedIntent.getStringExtra("name"));
                WordList.get(currentPosition).setPronounOfWord(receivedIntent.getStringExtra("pronoun"));
                WordList.get(currentPosition).setDescripOfWord(receivedIntent.getStringExtra("descrip"));
                WordList.get(currentPosition).setNotesOfWord(receivedIntent.getStringExtra("notes"));
                WordList.get(currentPosition).setRatingOfWord(receivedIntent.getDoubleExtra("rating",0));
                 AdapterListActivity.notifyDataSetChanged();

            }
    }

    private ArrayList<WordTemplate> CreateSamples(){
        ArrayList<WordTemplate> Sample = new ArrayList<>();
        Sample.add(new WordTemplate(R.drawable.buffalo,"Buffalo", "ˈbəf(ə)ˌlō","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplate(R.drawable.camel,"Camel", "ˈkaməl","a large, long-necked ungulate mammal of arid country, with long slender legs, broad cushioned feet, and either one or two humps on the back. Camels can survive for long periods without food or drink, chiefly by using up the fat reserves in their humps.","",0));
        Sample.add(new WordTemplate(R.drawable.cheetah,"Cheetah", "ˈCHēdə","a large slender spotted cat found in Africa and parts of Asia. It is the fastest animal on land.","",0));
        Sample.add(new WordTemplate(R.drawable.crocodile,"Crocodile", "ˈkräkəˌdīl","a large predatory semiaquatic reptile with long jaws, long tail, short legs, and a horny textured skin.","",0));
        Sample.add(new WordTemplate(R.drawable.elephant,"Elephant", "ˈeləfənt","a very large plant-eating mammal with a prehensile trunk, long curved ivory tusks, and large ears, native to Africa and southern Asia. It is the largest living land animal.","",0));
        Sample.add(new WordTemplate(R.drawable.giraffe,"Giraffe", "jəˈraf","a large African mammal with a very long neck and forelegs, having a coat patterned with brown patches separated by lighter lines. It is the tallest living animal.","",0));
        Sample.add(new WordTemplate(R.drawable.gnu,"Gnu", "n(y)o͞o","a large dark antelope with a long head, a beard and mane, and a sloping back.","",0));
        Sample.add(new WordTemplate(R.drawable.kudo,"Kudo", "ˈko͞odo͞o","an African antelope that has a greyish or brownish coat with white vertical stripes, and a short bushy tail. The male has long spirally curved horns.","",0));
        Sample.add(new WordTemplate(R.drawable.leopard,"Leopard", "ˈlepərd","a large solitary cat that has a fawn or brown coat with black spots, native to the forests of Africa and southern Asia.","",0));
        Sample.add(new WordTemplate(R.drawable.lion,"Lion", "ˈlīən","a large tawny-coloured cat that lives in prides, found in Africa and NW India. The male has a flowing shaggy mane and takes little part in hunting, which is done cooperatively by the females.","",0));
        Sample.add(new WordTemplate(R.drawable.oryx,"Oryx", "null","a large antelope living in arid regions of Africa and Arabia, having dark markings on the face and long horns.","",0));
        Sample.add(new WordTemplate(R.drawable.ostrich,"Ostrich", "ˈästriCH","a flightless swift-running African bird with a long neck, long legs, and two toes on each foot. It is the largest living bird, with males reaching a height of up to 2.75 m.","",0));
        Sample.add(new WordTemplate(R.drawable.shark,"Shark", "SHärk","a long-bodied chiefly marine fish with a cartilaginous skeleton, a prominent dorsal fin, and tooth-like scales. Most sharks are predatory, though the largest kinds feed on plankton, and some can grow to a large size.","",0));
        Sample.add(new WordTemplate(R.drawable.snake,"Snake", "snāk","a long limbless reptile which has no eyelids, a short tail, and jaws that are capable of considerable extension. Some snakes have a venomous bite.","",0));
        return Sample;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list_activity",  WordList);
    }
}
