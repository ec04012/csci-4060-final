package edu.uga.cs.ridesharefirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "RegisterActivity";

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText registerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById( R.id.register_email_editText);
        passwordEditText = findViewById( R.id.register_password_editText);
        registerName = findViewById(R.id.registerName);

        Button registerButton = findViewById(R.id.register_account_button);
        registerButton.setOnClickListener( new RegisterButtonClickListener() );
    } // RegisterActivity.onCreate()

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (emailEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("") || registerName.getText().toString().equals("")) {
                Toast.makeText( getApplicationContext(), "Please fill out the entire form to make an account.",  Toast.LENGTH_SHORT ).show();
            } else {
                // Get Strings from editTexts
                final String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final String name = registerName.getText().toString();

                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                // This is how we can create a new user using an email/password combination.
                // Note that we also add an onComplete listener, which will be invoked once
                // a new user has been created by Firebase.  This is how we will know the
                // new user creation succeeded or failed.
                // If a new user has been created, Firebase already signs in the new user;
                // no separate sign in is needed.
                firebaseAuth.createUserWithEmailAndPassword( email, password )
                    .addOnCompleteListener( RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText( getApplicationContext(),
                                    "Registered user: " + email,
                                    Toast.LENGTH_SHORT ).show();

                                // Sign in success, update UI with the signed-in user's information
                                Log.d( DEBUG_TAG, "createUserWithEmail: success" );

                                // Pass First and Last name into FirebaseAuth (aka Firebase profile)
                                // This is so that we can always access the logged-user's name elsewhere
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();
                                firebaseUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(DEBUG_TAG, "User Firebase profile updated.");
                                            } // if task.isSuccessful()
                                        } // onComplete()
                                    }); // addOnCompleteListener()

                                // Create equivalent User pojo so we can add to Firebase
                                User userPojo = new User(firebaseUser);
                                userPojo.setName(name);
                                userPojo.setPoints(FirebaseUtil.startingPointAmount);

                                // write user and points to firebase
                                FirebaseUtil.addUserToFirebase(userPojo);

                                // Transition to HomeActivity
                                Intent intent = new Intent( RegisterActivity.this, HomeActivity.class );
                                startActivity( intent );
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(DEBUG_TAG, "createUserWithEmail: failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                            } // if (task.isSuccessful()) - else
                        } // onCompleteListener()
                    }); // firebaseAuth.createUserWithEmailAndPassword( email, password ).addOnCompleteListener();
            } // if all fields are empty - else
        } // RegisterButtonClickListener.onClick()
    } // RegisterButtonClickListener

} // RegisterActivity
