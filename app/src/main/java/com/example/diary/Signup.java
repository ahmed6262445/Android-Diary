package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.EntityIterator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends AppCompatActivity {

    Button btnBack;
    Button btnLogin;
    Button btnNext;

    EditText etUsername;
    EditText etEmail;
    EditText etPassword;
    EditText etPasswordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initializeComponents();
    }

    public void initializeComponents() {
        this.btnBack = (Button) findViewById(R.id.btn_back);
        this.btnNext = (Button) findViewById(R.id.btn_next);
        this.btnLogin = (Button) findViewById(R.id.btn_login);

        this.btnNext.setOnClickListener(goToNextPage);
        this.btnBack.setOnClickListener(goToLoginPage);
        this.btnLogin.setOnClickListener(goToLoginPage);

        this.etUsername = (EditText) findViewById(R.id.et_username);
        this.etEmail = (EditText) findViewById(R.id.et_email);
        this.etPassword = (EditText) findViewById(R.id.et_password);
        this.etPasswordAgain = (EditText) findViewById(R.id.et_password_again);
    }

    // Click Listeners
    private View.OnClickListener goToLoginPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener goToNextPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Signup.this, SignupSecond.class);
            startActivity(intent);
        }
    };
}