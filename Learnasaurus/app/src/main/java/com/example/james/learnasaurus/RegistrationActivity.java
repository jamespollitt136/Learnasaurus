package com.example.james.learnasaurus;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    // AuthStateListener responds to changes in the user's sign-in state
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private String firstName;
    private String surname;
    private String fullName;
    private String email;
    private String password;

    private EditText regName;
    private EditText regSurname;
    private EditText regEmail;
    private EditText regPassword;
    private EditText confirmPass;

    private DialogController controller;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        // sets title displayed in app bar (top of app)
        setTitle("Register");

        controller = new DialogController(this);

        // binding widgets
        regName = (EditText)findViewById(R.id.firstNameInput);
        regSurname = (EditText)findViewById(R.id.surnameInput);
        regEmail = (EditText)findViewById(R.id.emailInput);
        regPassword = (EditText)findViewById(R.id.regPassInput);
        confirmPass = (EditText)findViewById(R.id.confirmPassInput);
        Button registerBtn = (Button)findViewById(R.id.registerButton);
        TextView signInLabel = (TextView)findViewById(R.id.signInTextView);

        // Firebase
        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
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
        // setting on click listeners for event handling
        registerBtn.setOnClickListener(regButtonListener);
        signInLabel.setOnClickListener(signInListener);
    }

    private void createUser(){
        Log.d(TAG, "createAccount: "+ email);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            controller.generateErrorDialog("Authentication failed");
                        }
                        else {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            if(firebaseUser != null) {
                                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(fullName).build();
                                firebaseUser.updateProfile(profile);

                                String userId = auth.getCurrentUser().getUid();
                                String displayName = auth.getCurrentUser().getDisplayName();
                                String email = auth.getCurrentUser().getEmail();
                                writeToDatabase(userId);
                                openMain(displayName, email);
                            }
                            else{
                                controller.generateErrorDialog("Something went wrong.");
                                regEmail.setText("");
                                regPassword.setText("");
                                confirmPass.setText("");
                            }
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

    private void writeToDatabase(String userId){
        databaseReference.child("users").child(userId).child("name").setValue(firstName);
        databaseReference.child("users").child(userId).child("surname").setValue(surname);
        databaseReference.child("users").child(userId).child("email").setValue(email);
        databaseReference.child("users").child(userId).child("score").setValue("0");
    }

    View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent signInIntent = new Intent(v.getContext(), LoginActivity.class);
            startActivity(signInIntent);
            finish();
        }
    };

    View.OnClickListener regButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final ProgressDialog regDialog = new ProgressDialog(RegistrationActivity.this, R.style.AppTheme_LoginDialog);
            regDialog.setIndeterminate(true);
            regDialog.setMessage("Registering...");
            regDialog.show();

            String nameInput = regName.getText().toString().trim();
            String surnameInput = regSurname.getText().toString().trim();
            String emailInput = regEmail.getText().toString().trim();
            String passInput = regPassword.getText().toString().trim();
            String confInput = confirmPass.getText().toString().trim();
            if(nameInput.equals("") || surnameInput.equals("") || emailInput.equals("")
                    || passInput.equals("") || confInput.equals("")){
                controller.generateErrorDialog("You have not completed all of the required fields.");
                confirmPass.setText("");
            }
            else {
                // check password length, has to be 6 or more for firebase to store
                if(passInput.length() >= 6){
                    // check password matches confirm password
                    if(confInput.equals(passInput)){
                        firstName = nameInput;
                        surname = surnameInput;
                        email = emailInput;
                        password = passInput;
                        fullName = firstName + " " + surname;
                        createUser();
                    }
                    else {
                        controller.generateErrorDialog("Passwords do not match, please try again.");
                        regPassword.setText("");
                        confirmPass.setText("");
                    }
                }
                else{
                    controller.generateErrorDialog("Passwords must be at least 6 characters");
                    regPassword.setText("");
                    confirmPass.setText("");
                }
            }
        }
    };

    private void openMain(String displayName, String email){
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra("displayName", displayName);
        mainIntent.putExtra("email", email);
        mainIntent.putExtra("password", password);
        startActivity(mainIntent);
        finish();
    }
}
