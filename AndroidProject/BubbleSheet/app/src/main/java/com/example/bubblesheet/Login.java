package com.example.bubblesheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    public static String email;
    TextView goto_register;
    TextInputLayout textinputUsername;
    TextInputLayout textinputPassword;
    TextView forgotPassword,register_now;
    Button login;
    Boolean valid = false;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textinputPassword = findViewById(R.id.textInput_Login_Password);
        textinputUsername = findViewById(R.id.textInput_Login_userName);
        login = findViewById(R.id.btn_loginStart);
        forgotPassword = findViewById(R.id.txt_forgetPassword);
        register_now=findViewById(R.id.txt_registerNow);
        progressBar = findViewById(R.id.progressbar);
        Start();
        register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Support.nextActivity(Login.this,register.class,"bottom-to-up");
                finish();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = textinputUsername.getEditText().getText().toString().trim();
                if (userName.isEmpty()) {
                    textinputUsername.setError("Field can't be empty");
                    valid = false;
                } else {
                    textinputUsername.setError(null);
                    valid = true;
                }
                if (valid) {
                    auth.sendPasswordResetEmail(userName).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Support.Alerter(Login.this, "Confirm", "Please check your email to reset your password !");
                            } else {
                                Support.Alerter(Login.this, "Failed", task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = textinputUsername.getEditText().getText().toString().trim();
                if (userName.isEmpty()) {
                    textinputUsername.setError("Field can't be empty");
                    valid = false;
                } else {
                    textinputUsername.setError(null);
                    valid = true;
                }
                String password = textinputPassword.getEditText().getText().toString();
                if (password.isEmpty()) {
                    textinputPassword.setError("Field can't be empty");
                    valid = false;
                } else if (password.contains(" ")) {
                    textinputPassword.setError("The password should't contains space");
                    valid = false;
                } else {
                    textinputPassword.setError(null);
                    valid = true;
                }

                if (valid) {
                    End();
                    email=userName;
                    auth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (auth.getCurrentUser().isEmailVerified()) {
                                    Start();
                                    Support.nextActivity(Login.this, navigation_home.class, "bottom-to-up");
                                    finish();
                                } else {
                                    Start();
                                    Support.Alerter(Login.this, "Warn", "Please verify your email address");
                                }
                            } else {
                                Start();
                                Support.Alerter(Login.this, "Failed", task.getException().getMessage());
                            }
                        }
                    });
                }


            }
        });
    }

    private void Start() {
        progressBar.setVisibility(View.GONE);
        textinputUsername.getEditText().setEnabled(true);
        textinputPassword.getEditText().setEnabled(true);
        forgotPassword.setEnabled(true);
        register_now.setEnabled(true);
        login.setEnabled(true);
    }

    private void End() {
        progressBar.setVisibility(View.VISIBLE);
        textinputUsername.getEditText().setEnabled(false);
        textinputPassword.getEditText().setEnabled(false);
        forgotPassword.setEnabled(false);
        register_now.setEnabled(false);
        login.setEnabled(false);
    }
}
