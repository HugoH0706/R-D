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
    EditText emailRegister, passwordRegister, nameRegister, emailLogin, passwordLogin;
    TextView tx1, tx2;
    String emailStr, passwordStr, nameStr;
    boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loggedIn = false;
        loginButton = (Button)findViewById(R.id.button2);                        //Login button
        registerButton = (Button)findViewById(R.id.button3);
        homeButton = (Button)findViewById(R.id.button);

        emailLogin = findViewById(R.id.editTextTextEmailAddress);
        passwordLogin = findViewById(R.id.editTextTextPassword);
        emailRegister = findViewById(R.id.editTextTextEmailAddress2);    //email testbalk
        passwordRegister = findViewById(R.id.editTextTextPassword2);        //password balk
        nameRegister = findViewById(R.id.editTextTextEmailAddress4);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                emailStr = emailRegister.getText().toString();
                passwordStr = passwordRegister.getText().toString();
                nameStr = nameRegister.getText().toString();

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("emailRegister", emailStr);
                editor.putString("passwordRegister", passwordStr);
                editor.putString("nameRegister", nameStr);
                editor.commit();
                Toast.makeText(LogIn.this, "Account details saved!", Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), emailStr+nameStr+passwordStr+"Ye", Toast.LENGTH_LONG).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                SharedPreferences sp2 = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
                String emailSaved = sp2.getString("emailRegister", "");
                String passwordSaved = sp2.getString("passwordRegister", "");
                String nameSaved = sp2.getString("nameRegister", "");

                if(emailLogin.getText().toString().equals(emailSaved) &&
                        passwordLogin.getText().toString().equals(passwordSaved)){
                    loggedIn = true;
                    Intent resultData = new Intent(LogIn.this, MainActivity.class);
                    resultData.putExtra("savedName", nameSaved);
                //    resultData.putExtra("savedPassword", passwordSaved);
                //    resultData.putExtra("savedEmail", emailSaved);
                    resultData.putExtra("loggedOn", loggedIn);
                    Toast.makeText(LogIn.this, "You have logged in successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(resultData);
                //    finish();
                } else{
                    Toast.makeText(LogIn.this, "Wrong email/password...", Toast.LENGTH_SHORT).show(); //getApplicationContext()
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });
    }
}