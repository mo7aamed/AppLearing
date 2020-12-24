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
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;

    public class LoginActivity extends AppCompatActivity {
        EditText emailId , passwordId;
        Button btnSignUp ,btnLogin ;
        FirebaseAuth mFirebaseAuth;
        private FirebaseAuth.AuthStateListener mAuthStateListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            mFirebaseAuth = FirebaseAuth.getInstance();
            emailId = findViewById(R.id.email_login);
            passwordId = findViewById(R.id.password_login);
            btnSignUp = findViewById(R.id.btn_signup2);
            btnLogin = findViewById(R.id.btn_login);
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {

                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser mfirebaseUser = mFirebaseAuth.getCurrentUser();
                    if(mfirebaseUser != null){
                        Toast.makeText(getApplicationContext(),"You are Logged In",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }
                    else {
                        Toast.makeText(getApplicationContext()," Please Login ",Toast.LENGTH_LONG).show();

                    }
                }
            };
            btnLogin.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(LoginActivity.this,"Fields is Empty!",Toast.LENGTH_LONG).show();
                    }
                    else if (!(email.isEmpty() && pass.isEmpty())){
                        mFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()){
                                    Toast.makeText(getApplication(),"Login is unSuccessful , please Login Again",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    startActivity(new Intent(LoginActivity.this ,HomeActivity.class));

                                }
                            }
                        });

                    }
                    else {
                        Toast.makeText(getApplication(),"Error Occurred",Toast.LENGTH_LONG).show();


                    }

                }

            });
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            });
        }

        @Override
        protected void onStart() {
            super.onStart();
            mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        }
    }