package com.example.projectgcdp;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


public class Register extends AppCompatActivity {
    EditText _etUsername, _etPassword;
    ProgressBar progressBar;
    Button btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _etUsername = findViewById(R.id.Username);
        _etPassword = findViewById(R.id.Password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btSubmit = findViewById(R.id.btSubmit);


        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                finish();
                Intent i = new Intent(Register.this, create.class);
                i.putExtra("email", _etUsername.getText().toString());
                i.putExtra("password", _etPassword.getText().toString());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


    }

    @SuppressLint("WrongConstant")
    private void registerUser() {
        String username = _etUsername.getText().toString().trim();
        String password = _etPassword.getText().toString().trim();

        if (username.isEmpty()) {
            _etUsername.setError("Email is required");
            _etUsername.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            _etUsername.setError("Please enter a valid email");
            _etUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            _etPassword.setError("Password is required");
            _etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            _etPassword.setError("Minimum lenght of password should be  6 ");
            _etPassword.requestFocus();
            return;
        }



    }
}
