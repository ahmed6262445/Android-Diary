package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

public class SignupSecond extends AppCompatActivity {

    Button btnBack;
    Button btnLogin;
    Button btnSignup;

    EditText etFullname;
    RadioGroup rgGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_second);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initializeComponents();
    }

    public void initializeComponents() {
        this.btnBack = (Button) findViewById(R.id.btn_back);
        this.btnSignup = (Button) findViewById(R.id.btn_signup);
        this.btnLogin = (Button) findViewById(R.id.btn_login);

        this.btnSignup.setOnClickListener(signUp);
        this.btnBack.setOnClickListener(goToSignupPage);
        this.btnLogin.setOnClickListener(goToLoginPage);

        this.etFullname = (EditText) findViewById(R.id.et_fullName);
        this.rgGender = (RadioGroup) findViewById(R.id.rg_gender);
        this.datePicker = (DatePicker) findViewById(R.id.date_picker);
    }

    // Click Listeners
    private View.OnClickListener goToLoginPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener goToSignupPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Signup.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener signUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(getApplicationContext(), SignupSecond.class);
//            startActivity(intent);
        }
    };
}