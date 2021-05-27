package com.example.wetmyplants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button signinButton;
    TextView loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signinButton = (Button)findViewById(R.id.button4);
        loggedInUser = (TextView)findViewById(R.id.textView6);
        loggedInUser.setText("Please sign in!");
        //loggedInUser.setVisibility(View.INVISIBLE);

        signinButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, LogIn.class);
                startActivity(intent);
            }
        });

        if(getIntent().getBooleanExtra("loggedOn", false)){
            loggedInUser.setText("Welcome "+getIntent().getStringExtra("savedName"));
            //loggedInUser.setVisibility(View.VISIBLE);
        }
    }
}