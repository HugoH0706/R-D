package com.example.wetmyplants;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;

        import com.example.wetmyplants.databinding.ActivityMainBinding;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.app.Activity;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;

public class LogIn extends AppCompatActivity {

    Button loginButton, registerButton, homeButton;
    EditText ed1, ed2;
    TextView tx1, tx2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginButton = (Button)findViewById(R.id.button2);                        //Login button
        registerButton = (Button)findViewById(R.id.button3);
        homeButton = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editTextTextEmailAddress);    //email testbalk
        ed2 = (EditText)findViewById(R.id.editTextTextPassword);        //password balk

        loginButton.setOnClickListener(new View.OnClickListener() {              //dit regelt wat er gebeurd als je op Login knop klikt
            public void onClick(View v){
                if(ed1.getText().toString().equals("test@outlook.com") &&    //uiteindelijk nog deze twee string veranderen naar de daadwerkelijke email en wachtwoord
                        ed2.getText().toString().equals("password")){
                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Wrong email/password...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(LogIn.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}