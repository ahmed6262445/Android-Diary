package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.diary.Models.User;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {

    Button btnBack;
    Button btnLogin;
    Button btnNext;

    TextInputLayout tilUsername;
    TextInputLayout tilEmail;
    TextInputLayout tilPassword;
    TextInputLayout tilPasswordConfirm;

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

        this.tilUsername = (TextInputLayout) findViewById(R.id.til_username);
        this.tilEmail = (TextInputLayout) findViewById(R.id.til_email);
        this.tilPassword = (TextInputLayout) findViewById(R.id.til_password);
        this.tilPasswordConfirm = (TextInputLayout) findViewById(R.id.til_password_confirm);
    }

    // Validation
    private boolean validateUsername() {
        String value = this.tilUsername.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        boolean isCorrect = true;

        if (value.isEmpty()) {
            this.tilUsername.setError("Field can not be empty");
            isCorrect = false;
        }
        else if (value.length()>20) {
            this.tilUsername.setError("Can not have more then 20 characters");
            isCorrect = false;
        }
        else if (!value.matches(checkspaces)) {
            this.tilUsername.setError("No white spaces are allowed!");
            isCorrect = false;
        }
        else {
            this.tilUsername.setError(null);
            this.tilUsername.setErrorEnabled(false);
            isCorrect = true;
        }
        return isCorrect;
    }

    private boolean validateEmail() {
        String value = this.tilEmail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        boolean isCorrect = true;

        if (value.isEmpty()) {
            this.tilEmail.setError("Field can not be empty");
            isCorrect = false;
        }
        else if (!value.matches(checkEmail)) {
            this.tilEmail.setError("Invalid Email!");
            isCorrect = false;
        }
        else {
            this.tilEmail.setError(null);
            this.tilEmail.setErrorEnabled(false);
            isCorrect = true;
        }
        return isCorrect;
    }

    private boolean validatePassword() {
        String value = this.tilPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character

                ".{10,}" +               //at least 4 characters
                "$";

        boolean isCorrect = true;

        if (value.isEmpty()) {
            this.tilPassword.setError("Field can not be empty");
            isCorrect = false;
        }
        else if (!value.matches(checkPassword)) {
            this.tilPassword.setError("Invalid Password!");

            this.tilPasswordConfirm.setError("Invalid Password!");
            isCorrect = false;
        }
        else {
            this.tilPassword.setError(null);
            this.tilPassword.setErrorEnabled(false);

            this.tilPasswordConfirm.setError(null);
            this.tilPasswordConfirm.setErrorEnabled(false);

            isCorrect = true;
        }
        return isCorrect;
    }

    private boolean validatePasswordConfirm() {
        String value = this.tilPasswordConfirm.getEditText().getText().toString().trim();

        boolean isCorrect = true;

        if (value.isEmpty()) {
            this.tilPasswordConfirm.setError("Field can not be empty");
            isCorrect = false;
        }
        else {
            this.tilPasswordConfirm.setError(null);
            this.tilPasswordConfirm.setErrorEnabled(false);
            isCorrect = true;
        }
        return isCorrect;
    }

    private boolean matchPasswords() {
        String password = this.tilPassword.getEditText().getText().toString().trim();
        String passwordConfirm = this.tilPasswordConfirm.getEditText().getText().toString().trim();

        boolean isMatch = true;

        if (!password.equals(passwordConfirm)) {
            this.tilPassword.setError("Passwords donot match");
            this.tilPasswordConfirm.setError("Passwords donot match");
            isMatch = false;
        }
        else {
            this.tilPassword.setError(null);
            this.tilPassword.setErrorEnabled(false);

            this.tilPasswordConfirm.setError(null);
            this.tilPasswordConfirm.setErrorEnabled(false);
            isMatch = true;
        }
        return isMatch;
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

            if (!validateUsername() | !validateEmail()  | !validatePassword() |
                    !validatePasswordConfirm()) {
                return;
            }

            if (!matchPasswords()) {
                return;
            }

            User user = new User(tilUsername.getEditText().getText().toString().trim(),
                    tilEmail.getEditText().getText().toString().trim(),
                    tilPassword.getEditText().getText().toString().trim());

            Intent intent = new Intent(Signup.this, SignupSecond.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    };
}