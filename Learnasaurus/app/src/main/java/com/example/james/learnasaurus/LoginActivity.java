package com.example.james.learnasaurus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private static final String TAG = "EmailPassword";

    private String userId;
    private String name;
    private String surname;
    private String email;
    private EditText emailField;
    private String password;
    private EditText passwordField;
    private boolean loginSuccess;

    private DialogController controller;

    private View view;

    View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent registerIntent = new Intent(v.getContext(), RegistrationActivity.class);
            startActivity(registerIntent);
            finish();
        }
    };

    View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final ProgressDialog loginDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_LoginDialog);
            loginDialog.setIndeterminate(true);
            loginDialog.setMessage("Authenticating...");
            loginDialog.show();
            view = v;
            if(emailField.getText().toString().length()==0 && passwordField.getText()
                    .toString().length()==0) {
                controller.generateErrorDialog("You are missing fields.");
                loginDialog.dismiss();
            }
            else {
                email = emailField.getText().toString();
                password = passwordField.getText().toString();
                signIn();
                loginDialog.dismiss();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        setTitle("Login");

        controller = new DialogController(this);

        TextInputLayout emailInputLayout = (TextInputLayout)findViewById(R.id.loginEmailLayout);
        TextInputLayout passInputLayout = (TextInputLayout)findViewById(R.id.loginPassLayout);

        emailField = (EditText) findViewById(R.id.loginUsername);
        passwordField = (EditText)findViewById(R.id.loginPassword);

        Button login = (Button)findViewById(R.id.loginButton);
        TextView registerLabel = (TextView)findViewById(R.id.registerTextView);

        // firebase
        auth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        registerLabel.setOnClickListener(registerListener);
        login.setOnClickListener(loginListener);
    }

    public void signIn(){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            loginSuccess = false;
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            controller.generateErrorDialog("Authentication failed");
                        }
                        else {
                            firebaseUser = auth.getCurrentUser();
                            databaseReference = FirebaseDatabase.getInstance().getReference();

                            userId = firebaseUser.getUid();

                            Intent homeIntent = new Intent(view.getContext(), MainActivity.class);
                            homeIntent.putExtra("name", name);
                            homeIntent.putExtra("surname", surname);
                            homeIntent.putExtra("email", email);
                            homeIntent.putExtra("password", password);
                            startActivity(homeIntent);
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
