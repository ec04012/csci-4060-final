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
        //registerButton.setOnClickListener( new RegisterButtonClickListener() );
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

            // Create an Intent to singin to Firebese.
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

            // For now, just log and toast
            Toast.makeText( getApplicationContext(),
                    "Sign-in successful:\nresponse.getEmail(): " + response.getEmail(),
                    Toast.LENGTH_SHORT).show();
            // Change this as needed
            // after a successful sign in, start the job leads management activity
            //Intent intent = new Intent( this, JobLeadManagementActivity.class );
            //startActivity( intent );
        }
        else {
            Log.d( DEBUG_TAG, "MainActivity.onSignInResult: Failed to sign in" );
            // Sign in failed. If response is null the user canceled the
            Toast.makeText( getApplicationContext(),
                    "Sign in failed",
                    Toast.LENGTH_SHORT).show();
        }
    } // onSignInResult()

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