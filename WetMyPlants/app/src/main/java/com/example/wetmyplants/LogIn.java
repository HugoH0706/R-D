package com.example.wetmyplants;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
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
    SharedPreferences sp;
    EditText email, password;
    TextView tx1, tx2;
    String emailStr, passwordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginButton = (Button)findViewById(R.id.button2);                        //Login button
        registerButton = (Button)findViewById(R.id.button3);
        homeButton = (Button)findViewById(R.id.button);
        email = findViewById(R.id.editTextTextEmailAddress);    //email testbalk
        password = findViewById(R.id.editTextTextPassword);        //password balk

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                emailStr = email.getText().toString();
                passwordStr = password.getText().toString();

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email", emailStr);
                editor.putString("password", passwordStr);
                editor.commit();
                Toast.makeText(LogIn.this, "Account details saved!", Toast.LENGTH_LONG).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {              //dit regelt wat er gebeurd als je op Login knop klikt
            public void onClick(View v){
                SharedPreferences sp2 = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
                String emailSaved = sp2.getString("email", "");
                String passwordSaved = sp2.getString("password", "");
                //Toast.makeText(LogIn.this, passwordSaved, Toast.LENGTH_LONG).show();
                if(email.getText().toString().equals(emailSaved) &&    //uiteindelijk nog deze twee string veranderen naar de daadwerkelijke email en wachtwoord
                        password.getText().toString().equals(passwordSaved)){
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