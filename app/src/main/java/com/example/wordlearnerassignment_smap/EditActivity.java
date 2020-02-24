package com.example.wordlearnerassignment_smap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditActivity extends AppCompatActivity {
private Button cancel;
private Button OK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        cancel = findViewById(R.id.ACTIVITY_EDIT_CANCEL_BUTTON2);
        OK = findViewById(R.id.ACTIVITY_EDIT_OK_BUT);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Remember to save changes
            }
        });
    }
}
