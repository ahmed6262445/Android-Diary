package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    Button btnLogin;
    Button btnSignup;
    Button btnForget;

    TextInputLayout tilUsername;
    TextInputLayout tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initializeComponents();
    }

    public void initializeComponents() {
        this.btnLogin = (Button) findViewById(R.id.btn_login);
        this.btnSignup = (Button) findViewById(R.id.btn_signup);
        this.btnForget = (Button) findViewById(R.id.btn_forget);

        this.btnLogin.setOnClickListener(login);
        this.btnSignup.setOnClickListener(goToSignupPage);
        this.btnForget.setOnClickListener(goToForgetPasswordPage);

        this.tilUsername = (TextInputLayout) findViewById(R.id.til_username);
        this.tilPassword = (TextInputLayout) findViewById(R.id.til_password);
    }

    // Validations
    private boolean validateUsername() {
        String value = this.tilUsername.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        boolean isValid = true;

        if (value.isEmpty()) {
            this.tilUsername.setError("Field can not be empty");
            isValid = false;
        }
        else {
            this.tilUsername.setError(null);
            this.tilUsername.setErrorEnabled(false);
            isValid = true;
        }
        return isValid;
    }

    private boolean validatePassword() {
        String value = this.tilPassword.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        boolean isValid = true;

        if (value.isEmpty()) {
            this.tilPassword.setError("Field can not be empty");
            isValid = false;
        }
        else {
            this.tilPassword.setError(null);
            this.tilPassword.setErrorEnabled(false);
            isValid = true;
        }
        return isValid;
    }

    // Click Listeners
    View.OnClickListener goToSignupPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Signup.class);
            startActivity(intent);
        }
    };

    View.OnClickListener goToForgetPasswordPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
            startActivity(intent);
        }
    };

    View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(!validateUsername() | !validatePassword()) {
                return;
            }
        }
    };
}