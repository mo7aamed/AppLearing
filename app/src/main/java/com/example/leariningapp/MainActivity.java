package com.example.leariningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText emailId , passwordId;
    Button btnSignup ,btnlogin ;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email_main);
        passwordId = findViewById(R.id.password_main);
        btnSignup = findViewById(R.id.btn_signup_main);
        btnlogin = findViewById(R.id.btn_login_main);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pass = passwordId.getText().toString();

                if (email.isEmpty()){
                    emailId.setError("Please enter your Email");
                    emailId.requestFocus();
                }
                else if (pass.isEmpty()){
                    passwordId.setError("Please enter your password correctly");
                    passwordId.requestFocus();
                }
                else if (email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields is Empty!",Toast.LENGTH_LONG).show();
                }
                else if (!(email.isEmpty() && pass.isEmpty())){
                mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getApplication(),"signup is unSuccessful , please try again",Toast.LENGTH_LONG).show();
                        }
                        else {
                            startActivity(new Intent(MainActivity.this ,HomeActivity.class));

                        }


                    }

                });

                }
                else {
                    Toast.makeText(getApplication(),"Error Occurred",Toast.LENGTH_LONG).show();


                }

            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }
}