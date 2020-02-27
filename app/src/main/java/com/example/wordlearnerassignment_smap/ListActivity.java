package com.example.wordlearnerassignment_smap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView RecyclerViewListActivity;
    private RecyclerView.Adapter AdapterListActivity;
    private RecyclerView.LayoutManager LayoutManagerListActivity;

    private Button PickStandin;
    private Button Exit;
    ArrayList<WordTemplateClass> WordList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        WordList = CreateSamples();

        RecyclerViewListActivity = findViewById(R.id.recyclerView);
        RecyclerViewListActivity.setHasFixedSize(true);
        LayoutManagerListActivity = new LinearLayoutManager(this);
        AdapterListActivity = new AdapterForWordList(WordList);

        RecyclerViewListActivity.setLayoutManager(LayoutManagerListActivity);
        RecyclerViewListActivity.setAdapter(AdapterListActivity);


        PickStandin = findViewById(R.id.PickButton_List_Test);
        PickStandin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToDetails = new Intent(ListActivity.this, DetailsActivity.class);
                startActivity(GoToDetails);
            }
        });
        Exit = findViewById(R.id.Exit_button_List);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private ArrayList<WordTemplateClass> CreateSamples(){
        ArrayList<WordTemplateClass> Sample = new ArrayList<>();
        Sample.add(new WordTemplateClass(R.drawable.buffalo,"Buffalo", "ˈbəf(ə)ˌlō","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.camel,"Camel", "ˈkaməl","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.cheetah,"Cheetah", "ˈCHēdə","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.crocodile,"Crocodile", "ˈkräkəˌdīl","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.elephant,"Elephant", "ˈeləfənt","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.giraffe,"Giraffe", "jəˈraf","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.gnu,"Gnu", "n(y)o͞o","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.kudo,"Kudo", "ˈko͞odo͞o","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.leopard,"Leopard", "ˈlepərd","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.lion,"Lion", "ˈlīən","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.oryx,"Oryx", "null","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.ostrich,"Ostrich", "ˈästriCH","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.shark,"Shark", "SHärk","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        Sample.add(new WordTemplateClass(R.drawable.snake,"Snake", "snāk","a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics.","",0));
        return Sample;
    }
}
