package com.example.wetmyplants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button signInButton, buttonToRequest;
    TextView loggedInUser;
    private boolean loggedOn;
    private ArrayList<String> fruits;
    boolean userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonToRequest = (Button)findViewById(R.id.button7);
       // myGardenButton = (Button)findViewById(R.id.button8);
        signInButton = (Button)findViewById(R.id.button5);
        loggedInUser = (TextView)findViewById(R.id.textView6);
        loggedInUser.setText("Please sign in!");

        fruits = new ArrayList<>();

        signInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, LogIn.class);
                startActivity(intent);
            }
        });

        buttonToRequest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, RequestAPI.class);
                //userLoggedIn = getIntent().getBooleanExtra("loggedOn", false);
                //intent.putExtra("loggedOn", userLoggedIn);
                startActivity(intent);
            }
        });

       loggedOn = getIntent().getBooleanExtra("loggedOn", false);
        if(loggedOn){
            loggedInUser.setText("Welcome "+getIntent().getStringExtra("savedName"));
        }

       /* myGardenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyGarden.class);

                Bundle bundle = getIntent().getExtras();
                if(bundle != null ){ //&& bundle.containsKey("addedFruits") && bundle.containsKey("loggedIn")
                    fruits = getIntent().getStringArrayListExtra("addedFruits");
                    intent.putExtra("addedFruits", fruits);
                    intent.putExtra("loggedIn", loggedOn);
                }
                startActivity(intent);
            }
        });*/

    }
}