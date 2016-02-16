package com.teamoptimal.cse110project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {

    ImageButton imgButton;
    //FloatingActionButton addButton;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imgButton =(ImageButton)findViewById(R.id.findrr_button);

        imgButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imgButton.setColorFilter(Color.argb(255,255,255,255)); //Tint when clicked
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        addButton = (Button) findViewById(R.id.addrr_button);
        //addButton = (FloatingActionButton) findViewById(R.id.addrr_button;
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateRestroomActivity.class);
                startActivity(intent);
            }
        });



    }





}