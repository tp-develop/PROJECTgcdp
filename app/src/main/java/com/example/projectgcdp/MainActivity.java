package com.example.projectgcdp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    ViewGroup layout;
    ImageView imLogo;
    TextView tvLogo;
    Button btLogin,btRegis;
    ProgressBar progressBar;
    public EditText _etUsername, _etPassword;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin);

        mAuth = FirebaseAuth.getInstance();
        layout = (ViewGroup) findViewById(R.id.layout);
        imLogo = (ImageView) findViewById(R.id.imageView);
        tvLogo = (TextView) findViewById(R.id.textView);
        _etUsername = (EditText) findViewById(R.id.etUsername);
        _etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegis = (Button)findViewById(R.id.tvRegis);
        progressBar = findViewById(R.id.progressBar);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserLogin(); }
        });

        btRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(MainActivity.this, Register.class)); }
        });

    }


    private void UserLogin() {

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

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                        finish();
                        Intent i = new Intent(new Intent(MainActivity.this, Home.class));
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);




                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(MainActivity.this, Home.class));
        }
    }

}

