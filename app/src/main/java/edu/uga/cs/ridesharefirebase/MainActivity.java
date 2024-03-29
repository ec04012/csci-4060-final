package edu.uga.cs.ridesharefirebase;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d( DEBUG_TAG, "JobLead: MainActivity.onCreate()" );

        Button signInButton = findViewById( R.id.sign_in_button );
        Button registerButton = findViewById( R.id.register_button );

        signInButton.setOnClickListener( new SignInButtonClickListener() );
        registerButton.setOnClickListener( new RegisterButtonClickListener() );

        /*
        // Automatically sign-in
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String email = "test@email.com";
        String password = "password";

        mAuth.signInWithEmailAndPassword( email, password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d( DEBUG_TAG, " signInWithEmail:success" );
                            Toast.makeText( MainActivity.this, "Authentication succeeded.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.d( DEBUG_TAG, " signInWithEmail:failure", task.getException() );
                            Toast.makeText( MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
         */
    } // MainActivity onCreate()

    // A button listener class to start a Firebase sign-in process
    private class SignInButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick( View v ) {
            // This is an example of how to use the AuthUI activity for signing in to Firebase.
            // Here, we are just using email/password sign in.
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
            );

            Log.d( DEBUG_TAG, "MainActivity.SignInButtonClickListener: Signing in started" );

            // Create an Intent to sign-in to Firebese.
            Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                // this sets our own theme (color scheme, sizing, etc.) for the AuthUI's appearance
                //.setTheme(R.style.LoginTheme)
                .build();
            signInLauncher.launch(signInIntent);
        } //SignInButtonClickListener.onClick()
    } //SignInButtonClickListener

    // The ActivityResultLauncher class provides a new way to invoke an activity
    // for some result.  It is a replacement for the deprecated method startActivityForResult.
    //
    // The signInLauncher variable is a launcher to start the AuthUI's logging in process that
    // should return to the MainActivity when completed.  The overridden onActivityResult
    // is then called when the Firebase logging-in process is finished.
    private ActivityResultLauncher<Intent> signInLauncher =
        registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
        ); // signInLauncher

    // This method is called once the Firebase sign-in activity (launched above) returns (completes).
    // Then, the current (logged-in) Firebase user can be obtained.
    // Subsequently, there is a transition to the JobLeadManagementActivity.
    private void onSignInResult( FirebaseAuthUIAuthenticationResult result ) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            if( response != null ) {
                Log.d( DEBUG_TAG, "MainActivity.onSignInResult: response.getEmail(): " + response.getEmail() );
            }

            //Log.d( DEBUG_TAG, "MainActivity.onSignInResult: Signed in as: " + user.getEmail() );
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseUserMetadata metadata = firebaseUser.getMetadata();

            Log.d("TAG", "isNewUser= " + response.isNewUser());

            // Create Firebase entry for user if new user, and show appropriate toasts
            if (response.isNewUser()) {
                // The user is new, show them a fancy intro screen!
                // Show Toast
                Toast.makeText( getApplicationContext(),
                    "Thank you for registering!: " + response.getEmail(),
                    Toast.LENGTH_SHORT).show();

                // Create User and point total, then write to firebase
                User userPojo = new User(firebaseUser);
                userPojo.setPoints(FirebaseUtil.startingPointAmount);
                FirebaseUtil.addUserToFirebase(userPojo);
            } else {
                // This is an existing user, show them a welcome back screen.
                // Show Toast and start HomeActivity
                Toast.makeText( getApplicationContext(),
                    "Sign-in successful: " + response.getEmail(),
                    Toast.LENGTH_SHORT).show();
            } // if-else user is a new user (i.e. just registered)

            Intent intent = new Intent(this, HomeActivity.class);
            this.startActivity(intent);
        } // if result.getResultCode() == RESULT_OK
        else {
            Log.d( DEBUG_TAG, "MainActivity.onSignInResult: Failed to sign in" );
            // Sign in failed. If response is null the user canceled the
            Toast.makeText( getApplicationContext(),
                    "Sign in failed",
                    Toast.LENGTH_SHORT).show();
        } // else (i.e. if result not ok)
    } // onSignInResult()

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start the user registration activity
            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            view.getContext().startActivity(intent);
        } // RegisterButtonClickListener.onClick()
    } // RegisterButtonClickListener

    // These activity callback methods are not needed and are for edational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "RideShare: MainActivity.onStart()" );
        super.onStart();
    } //onStart()

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "RideShare: MainActivity.onResume()" );
        super.onResume();
    } //onResume()

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "RideShare: MainActivity.onPause()" );
        super.onPause();
    } //onPause()

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "RideShare: MainActivity.onStop()" );
        super.onStop();
    } //onStop()

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "RideShare: MainActivity.onDestroy()" );
        super.onDestroy();
    } //onDestroy()

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "RideShare: MainActivity.onRestart()" );
        super.onRestart();
    } //onRestart()

} // MainActivity