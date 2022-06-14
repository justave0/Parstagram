package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.window.SplashScreen;

import com.bumptech.glide.Glide;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btLogin;
    Button btCreate;
    ImageView ivLogo;
    public static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ParseUser.getCurrentUser();
        setContentView(R.layout.activity_login);

        //Login persistance
        if (ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }


        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btLogin = findViewById(R.id.btLogin);
        ivLogo = findViewById(R.id.ivLoginLogo);
        btCreate = findViewById(R.id.btLoginCreateAccount);
        Glide.with(LoginActivity.this).load(R.drawable.nav_logo_whiteout).override(765,324).into(ivLogo);

        btLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(etUsername.getText().toString(), etPassword.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, com.parse.ParseException e) {
                                if (user != null) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.e(TAG, e.toString());
                                }
                    }
                });
            }
        });

        btCreate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}