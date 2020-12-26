package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    Button btnLogin;
    Button btnSignup;
    Button btnForget;

    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeComponents();
    }

    public void initializeComponents() {
        this.btnLogin = (Button) findViewById(R.id.btn_login);
        this.btnSignup = (Button) findViewById(R.id.btn_signup);
        this.btnForget = (Button) findViewById(R.id.btn_forget);

        this.btnLogin.setOnClickListener(login);
        this.btnSignup.setOnClickListener(goToSignupPage);
        this.btnForget.setOnClickListener(goToForgetPasswordPage);

        this.etUsername = (EditText) findViewById(R.id.et_username);
        this.etPassword = (EditText) findViewById(R.id.et_password);
    }

    // Click Listeners
    View.OnClickListener goToSignupPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            }
    };

    View.OnClickListener goToForgetPasswordPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };
}