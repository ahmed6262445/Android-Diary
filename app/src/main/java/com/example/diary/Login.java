package com.example.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

public class Login extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    Button btnLogin;
    Button btnSignup;
    Button btnForget;

    TextInputLayout tilEmail;
    TextInputLayout tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initializeComponents();
    }

    public void initializeComponents() {
        this.firebaseAuth = FirebaseAuth.getInstance();

        this.btnLogin = (Button) findViewById(R.id.btn_login);
        this.btnSignup = (Button) findViewById(R.id.btn_signup);
        this.btnForget = (Button) findViewById(R.id.btn_forget);

        this.btnLogin.setOnClickListener(login);
        this.btnSignup.setOnClickListener(goToSignupPage);
        this.btnForget.setOnClickListener(goToForgetPasswordPage);

        this.tilEmail = (TextInputLayout) findViewById(R.id.til_email);
        this.tilPassword = (TextInputLayout) findViewById(R.id.til_password);
    }

    // Functions
    public void login() {
        String email = this.tilEmail.getEditText().getText().toString();
        String password = this.tilPassword.getEditText().getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    //FirebaseUser user = mAuth.getCurrentUser();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
//                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

//                    updateUI(null);
                }
            }
        });
    }

    // Validations
    private boolean validateUsername() {
        String value = this.tilEmail.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        boolean isValid = true;

        if (value.isEmpty()) {
            this.tilEmail.setError("Field can not be empty");
            isValid = false;
        }
        else {
            this.tilEmail.setError(null);
            this.tilEmail.setErrorEnabled(false);
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

            login();
        }
    };
}