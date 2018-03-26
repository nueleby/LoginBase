package com.example.ebyzy.loginbase;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnSignUp, btnLogin, btnReset;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        inputEmail = (EditText)findViewById(R.id.email);
        inputPassword = (EditText)findViewById(R.id.password);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnSignUp = (Button)findViewById(R.id.btn_signUp);
        btnReset = (Button)findViewById(R.id.btn_reset_password);

        auth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String email = inputEmail.getText().toString();
             final String password = inputPassword.getText().toString();
             
             if (TextUtils.isEmpty(email)){
                 Toast.makeText(getApplicationContext(), "Enter Email Address!",
                         Toast.LENGTH_SHORT).show();
                 return;
             }
             if (TextUtils.isEmpty(password)){
                 Toast.makeText(getApplicationContext(),"Enter Password!",
                         Toast.LENGTH_SHORT).show();
                 return;
             }
             progressBar.setVisibility(View.VISIBLE);

             auth.signInWithEmailAndPassword(email, password)
                     .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (!task.isSuccessful()){
                                 if (password.length()<6 ){
                                     inputPassword.setError(getString(R.string.minimum_password));
                                 }
                                 else {
                                     Toast.makeText(LoginActivity.
                                             this, getString(R.string.auth_failed),
                                             Toast.LENGTH_SHORT).show();
                                 }
                             }else {
                                 Intent intent = new Intent(LoginActivity.this,
                                         MainActivity.class);

                                     startActivity(intent);
                                 finish();

                             }

                         }
                     });
            }
        });

    }
}
