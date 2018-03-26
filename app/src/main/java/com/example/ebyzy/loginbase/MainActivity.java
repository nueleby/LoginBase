package com.example.ebyzy.loginbase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser, changeEmail,
    changePassword, sendEmail, remove, signOut;
    private EditText oldEmail, newEmail, newPassword, password;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        auth = FirebaseAuth.getInstance();

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser ==null){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class ));
                    finish();
                }
            }
        };
        btnChangeEmail = (Button) findViewById(R.id.change_email_button);
        btnChangePassword = (Button) findViewById(R.id.change_password_button);
        btnRemoveUser = (Button) findViewById(R.id.remove_user_button);
        btnSendResetEmail = (Button) findViewById(R.id.sending_pass_reset_button);
        changeEmail = (Button) findViewById(R.id.changeEmail);
        changePassword = (Button)findViewById(R.id.changePassword);
        sendEmail = (Button) findViewById(R.id.send);
        remove = (Button) findViewById(R.id.remove);
        signOut = (Button) findViewById(R.id.sign_out);

        oldEmail = (EditText) findViewById(R.id.oldEmail);
        newEmail = (EditText) findViewById(R.id.newEmail);
        password = (EditText) findViewById(R.id.password);
        newPassword = (EditText) findViewById(R.id.newPassword);

        oldEmail.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        changeEmail.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null){
            progressBar.setVisibility(View.GONE);
        }
        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldEmail.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (firebaseUser != null && !newEmail.getText().toString().trim().equals("")){
                       firebaseUser.updateEmail(newEmail.getText().toString().trim())
                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if (task.isSuccessful()){
                                           Toast.makeText(MainActivity.this,
                     "Email address is updated. please sign in with new email",
                                                   Toast.LENGTH_LONG).show();
                                           signOut();
                                           progressBar.setVisibility(View.GONE);
                                       } else {Toast.makeText(MainActivity.this,
                                               "Email not updated",Toast.LENGTH_LONG).show();
                                       progressBar.setVisibility(View.GONE);
                                       }

                                   }
                               });
                    }else if (newEmail.getText().toString().trim().equals("")){
                        newEmail.setError("Enter Email");
                        progressBar.setVisibility(View.GONE);
                    }
            }
        });
btnChangePassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        oldEmail.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        changeEmail.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);

    }
});
changePassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        if (firebaseUser != null && newPassword.getText().toString().trim().equals("")){
           if (newPassword.getText().toString().trim().length()<6){
               newPassword.setError("Password too short, Enter Minimum of 6 Characters");
               progressBar.setVisibility(View.GONE);
           }else {
               firebaseUser.updatePassword(newPassword.getText().toString().trim())
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()){
                                 Toast.makeText(MainActivity.this,
                                         "Password is updated. sign in with new password",
                                         Toast.LENGTH_SHORT).show();
                                 signOut();
                                 progressBar.setVisibility(View.GONE);
                               }else { Toast.makeText(MainActivity.this, "Password " +
                                       "Update failed",Toast.LENGTH_SHORT).show();
                               progressBar.setVisibility(View.GONE);
                               }
                           }
                       });
           }
        }else if (newPassword.getText().toString().trim().equals("")){
            newPassword.setError("Enter Password");
            progressBar.setVisibility(View.GONE);
        }
    }
});
btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        if ( !oldEmail.getText().toString().trim().equals("") ){
            auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this,
                                        "Reset password email sent!"
                                ,Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }else {
                                Toast.makeText(MainActivity.this,
                                        "Failed to send reset password email",
                                        Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }else {
            oldEmail.setError("Enter Email");
            progressBar.setVisibility(View.GONE);
        }
    }
});
btnRemoveUser.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        if (firebaseUser != null){
            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Your profile is deleted:) " +
                                "please create a new account now!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SignUpActivity.class));

                        finish();

                        progressBar.setVisibility(View.GONE);
                    }else {Toast.makeText(MainActivity.this, "failed to delete account!",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                    }
                }
            });
        }
    }
});
signOut.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        signOut();
    }
});

    }
    public void signOut(){
        auth.signOut();
    }
    @Override
    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public  void onStart(){
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if ( authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

}
