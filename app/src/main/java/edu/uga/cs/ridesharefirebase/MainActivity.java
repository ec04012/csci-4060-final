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
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById( R.id.textView );

        mAuth = FirebaseAuth.getInstance();
        final String email = "dawg@mail.com";
        final String password = "password";

        mAuth.signInWithEmailAndPassword( email, password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d( DEBUG_TAG, " signInWithEmail:success" );
                            Log.d( DEBUG_TAG, String.format("email:%s, password:%s", email, password) );
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.d( DEBUG_TAG, " signInWithEmail:failure", task.getException() );
                            Log.d( DEBUG_TAG, String.format("email:%s, password:%s", email, password) );
                            Toast.makeText( MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference( "message" );

        // Read from the database value for ”message”
        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String message = dataSnapshot.getValue( String.class );
                textView.setText( textView.getText().toString() + "\n\n" + message );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d( DEBUG_TAG, " Failed to read value.", error.toException() );
            }
        }); // firebase read listener

        final String email2 = "test@email.com";
        final String password2 = "password";

        mAuth.signInWithEmailAndPassword( email2, password2 )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d( DEBUG_TAG, " signInWithEmail:success" );
                            Log.d( DEBUG_TAG, String.format("email:%s, password:%s", email2, password2) );
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.d( DEBUG_TAG, " signInWithEmail:failure", task.getException() );
                            Log.d( DEBUG_TAG, String.format("email:%s, password:%s", email2, password2) );
                            Toast.makeText( MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    } // MainActivity onCreate()

    // These activity callback methods are not needed and are for edational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onStart()" );
        super.onStart();
    } //onStart()

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onResume()" );
        super.onResume();
    } //onResume()

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onPause()" );
        super.onPause();
    } //onPause()

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onStop()" );
        super.onStop();
    } //onStop()

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onDestroy()" );
        super.onDestroy();
    } //onDestroy()

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "JobLead: MainActivity.onRestart()" );
        super.onRestart();
    } //onRestart()

} // MainActivity