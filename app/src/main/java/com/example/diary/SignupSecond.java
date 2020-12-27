package com.example.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.diary.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SignupSecond extends AppCompatActivity {

    User user;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    Button btnBack;
    Button btnLogin;
    Button btnSignup;

    TextInputLayout tilFullname;
    RadioGroup rgGender;
    RadioButton rbMale;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_second);
        FirebaseApp.initializeApp(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initializeComponents();
    }

    public void initializeComponents() {

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();

        this.btnBack = (Button) findViewById(R.id.btn_back);
        this.btnSignup = (Button) findViewById(R.id.btn_signup);
        this.btnLogin = (Button) findViewById(R.id.btn_login);

        this.btnSignup.setOnClickListener(signUp);
        this.btnBack.setOnClickListener(goToSignupPage);
        this.btnLogin.setOnClickListener(goToLoginPage);

        this.tilFullname = (TextInputLayout) findViewById(R.id.til_fullName);
        this.rgGender = (RadioGroup) findViewById(R.id.rg_gender);
        this.rbMale = (RadioButton) findViewById(R.id.rb_male);
        this.rbMale.setChecked(true);
        this.datePicker = (DatePicker) findViewById(R.id.date_picker);
    }

    // Validations
    private boolean validateUsername() {
        String value = this.tilFullname.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        boolean isValid = true;

        if (value.isEmpty()) {
            this.tilFullname.setError("Field can not be empty");
            isValid = false;
        }
        else if (value.length()>50) {
            this.tilFullname.setError("Can not have more then 50 characters");
            isValid = false;
        }
        else {
            this.tilFullname.setError(null);
            this.tilFullname.setErrorEnabled(false);
            isValid = true;
        }
        return isValid;
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int age = currentYear - userAge;

        boolean isValid = true;
        if (age < 0) {
            Toast.makeText(getApplicationContext(), "Please enter valid age", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if (age < 5) {
            Toast.makeText(getApplicationContext(), "You are not eligible to apply", Toast.LENGTH_LONG).show();
            isValid = false;
        }

        return isValid;
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
            if (!validateUsername() | !validateAge()) {
                return;
            }

            Intent oldIntent = getIntent();
            user = (User) oldIntent.getSerializableExtra("user");

            user.setFullname(tilFullname.getEditText().getText().toString().trim());
            user.setDay(datePicker.getDayOfMonth());
            user.setMonth(datePicker.getMonth());
            user.setYear(datePicker.getYear());

            if (rbMale.isChecked()) {
                user.setMale(true);
            }
            else {
                user.setMale(false);
            }

            register();
//            Intent intent = new Intent(getApplicationContext(), SignupSecond.class);
//            startActivity(intent);
        }
    };


    public void register() {
        this.firebaseAuth.createUserWithEmailAndPassword(this.user.getEmail(), this.user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user.setId(firebaseAuth.getCurrentUser().getUid());
                            DocumentReference documentReference = firestore.collection("users").document(user.getId());

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Sign up done!\nYou may proceed",
                                            Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onSucess: user profile is created for " + user.getId());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Unable to register user at the moment\n" +
                                                    "Please try later",
                                            Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onFail: Unable to register user");

                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                }
                            });

                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Unable to register user at the moment\n" +
                                    "Please try later",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}