package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

public class SignUpActivity extends AppCompatActivity {
    ImageView ivSignUpLogo;
    TextView tvSignUp;
    EditText etSignUpUsername;
    EditText etSignUpPassword;
    EditText etSignUpEmail;
    Button btSignUp;
    private String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ivSignUpLogo = findViewById(R.id.ivSignUpLogo);
        Glide.with(SignUpActivity.this).load(R.drawable.nav_logo_whiteout).override(765,324).into(ivSignUpLogo);

        tvSignUp = findViewById(R.id.tvSignUp);
        etSignUpUsername = findViewById(R.id.etSignUpUsername);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etSignUpEmail = findViewById(R.id.etSignUpEmail);
        btSignUp = findViewById(R.id.btSignUp);


        ParseUser user = new ParseUser();

        btSignUp.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Set core properties
                user.setUsername(etSignUpUsername.getText().toString());
                user.setPassword(etSignUpPassword.getText().toString());
                user.setEmail(etSignUpEmail.getText().toString());
                // Set custom properties
                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.e(TAG, e.toString());
                        }
                    }
                });
            }
        });
    }
}
