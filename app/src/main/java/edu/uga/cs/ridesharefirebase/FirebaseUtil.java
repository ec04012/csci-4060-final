package edu.uga.cs.ridesharefirebase;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtil {
    private static String DEBUG_TAG = "FirebaseUtil";
    private static FirebaseDatabase database;

    /**
     * Returns a list of every ride in the Firebase.
     * @return a list containing every ride in the Firebase.
     */
    public static List<Ride> getAllRides() {
        List<Ride> rideList = new ArrayList<>();

        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rides");

        // Set up a listener (event handler) to receive a value for the database reference.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method
        // and then each time the value at Firebase changes.
        //
        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, we need to iterate over the elements and place them on our job lead list.
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey( postSnapshot.getKey() );
                    rideList.add( ride );
                    Log.d( DEBUG_TAG, "ValueEventListener: added: " + ride );
                    //Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                    Log.d(DEBUG_TAG, "");
                } // for every element in firebase
                /* These next two lines update the UI. RN, this method just gets the list of rides. We can handle UI here, or somewhere else. */
                //Log.d( DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter" );
                //recyclerAdapter.notifyDataSetChanged();
            } // onDataChange()

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            } // onCancelled()
        } ); // DatabaseReference.addValueEventListener()
        return rideList;
    } //getRide
} // FirebaseUtil
