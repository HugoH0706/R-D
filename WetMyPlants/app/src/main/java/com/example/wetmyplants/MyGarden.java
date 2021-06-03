package com.example.wetmyplants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyGarden extends AppCompatActivity {

    private TextView view;
    private Button homeButton;
    SharedPreferences sp3;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_garden);

        view = (TextView)findViewById(R.id.textView5);
        view.append("\n");
        homeButton = (Button)findViewById(R.id.button12);
        sp3 = getSharedPreferences("MyUserPrefs2", Context.MODE_PRIVATE);

        counter = getIntent().getIntExtra("itemsAdded", 0);
        addFruitsToView();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addFruitsToView(){
        SharedPreferences sp4 = getApplicationContext().getSharedPreferences("MyFruits", Context.MODE_PRIVATE);

        int totalSaved = sp4.getInt("itemsAdded", 0);
        for(int i = 0; i < totalSaved; i++){
            view.append("- ");
            view.append(sp4.getString("fruit"+String.valueOf(i), ""));
            view.append("\n");
        }
    }
}