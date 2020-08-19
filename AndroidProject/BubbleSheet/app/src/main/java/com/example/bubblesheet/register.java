package com.example.bubblesheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


import maes.tech.intentanim.CustomIntent;

public class register extends AppCompatActivity {

    TextInputLayout textinputUserName, textinputPassword, textinputrPassword, textinputEmail;
    Button btn_register;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;
    ProgressBar progressBar;
    boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textinputUserName = findViewById(R.id.textInput_Login_userName);
        textinputPassword = findViewById(R.id.textInput_Login_userPassword);
        textinputrPassword = findViewById(R.id.textInput_Login_userPassword_confirm);
        textinputEmail = findViewById(R.id.textInput_Login_userEmail);
        btn_register = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressbar);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        End();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = textinputUserName.getEditText().getText().toString().trim();
                if (userName.isEmpty()) {
                    textinputUserName.setError("Field can't be empty");
                    valid = false;
                } else {
                    textinputUserName.setError(null);
                    valid = true;

                }

                final String email = textinputEmail.getEditText().getText().toString().trim();
                if (email.isEmpty()) {
                    textinputEmail.setError("Field can't be empty");
                    valid = false;

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    textinputEmail.setError("Please ,enter a valid email address");
                    valid = false;
                } else {
                    textinputEmail.setError(null);
                    valid = true;

                }

                final String password = textinputPassword.getEditText().getText().toString();
                final String repassword = textinputrPassword.getEditText().getText().toString();
                if (password.isEmpty()) {
                    textinputPassword.setError("Field can't be empty");
                    valid = false;

                } else if (!password.equals(repassword)) {
                    textinputPassword.setError("not matching");
                    textinputrPassword.setError("not matching");
                    valid = false;
                } else if (password.length() < 6) {
                    textinputPassword.setError("The password must be at least 6 characters");
                    valid = false;

                } else if (password.contains(" ")) {
                    textinputPassword.setError("The password should't contains space");
                    valid = false;
                } else {
                    textinputPassword.setError(null);
                    valid = true;
                }
                if (valid) {
                    Start();
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            End();
                                            Support.Alerter(register.this, "Success", "Registered Successfully.Please check your email for verification");
                                            User user = new User(email, userName, password);
                                            firestore.collection("users").document(email).collection("info").document("info").set(user);
                                            firestore.collection("users").document(email).collection("exams");

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Support.nextActivity(register.this,Login.class,"left-to-right");
                                                    finish();
                                                }
                                            },4000);
                                        } else {
                                            End();
                                            Support.Alerter(register.this, "Error", task.getException().getMessage());
                                        }
                                    }
                                });
                            } else {
                                End();
                                Support.Alerter(register.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }

            }
        });

    }

    private void Start() {
        progressBar.setVisibility(View.VISIBLE);
        btn_register.setEnabled(false);
        textinputEmail.getEditText().setEnabled(false);
        textinputrPassword.getEditText().setEnabled(false);
        textinputPassword.getEditText().setEnabled(false);
        textinputUserName.getEditText().setEnabled(false);
    }

    private void End() {
        progressBar.setVisibility(View.GONE);
        btn_register.setEnabled(true);
        textinputEmail.getEditText().setEnabled(true);
        textinputrPassword.getEditText().setEnabled(true);
        textinputPassword.getEditText().setEnabled(true);
        textinputUserName.getEditText().setEnabled(true);
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(register.this, "up-to-bottom");
    }
}
