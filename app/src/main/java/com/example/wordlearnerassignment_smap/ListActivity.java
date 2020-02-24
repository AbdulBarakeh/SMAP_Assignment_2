package com.example.wordlearnerassignment_smap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListActivity extends AppCompatActivity {

    private Button PickStandin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        PickStandin = findViewById(R.id.PickButton_List_Test);
        PickStandin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToDetails = new Intent(ListActivity.this, DetailsActivity.class);
                startActivity(GoToDetails);
            }
        });

    }



}
