package edu.uga.cs.ridesharefirebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseUtil {
    public static int startingPointAmount = 300;

    private static String DEBUG_TAG = "FirebaseUtil";
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    /**
     * Add the given user to the Firebase.
     * @param user
     */
    public static void addUserToFirebase(User user) {
        // Add a new element (User) to the list of Users in Firebase.

        // Get references to firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        // First, a call to push() appends a new node to the existing list (one is created
        // if this is done for the first time).  Then, we set the value in the newly created
        // list node to store the new job lead.
        // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
        // the previous apps to maintain job leads.
        myRef.push().setValue( user )
            .addOnSuccessListener( new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Show a quick confirmation
                    //FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
                    //Toast.makeText(getActivity().getApplicationContext(), "User Created" + "UID = " + mFirebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                    // Clear the EditTexts for next use.
                } // onSucess()
            }) // .addOnSuccessListener()
            .addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure( @NonNull Exception e ) {
                    // do nothing
                } // onFailure()
            }); // .addOnSuccessListener()
    } // addUserToFirebase()

    /**
     * Returns a list of every ride in the Firebase.
     * Currently, this method does not correctly return the list because
     * it is asychronous. The method returns rideList before the firebase
     * returns its data.
     * @return a list containing every ride in the Firebase.
     */
    public static ArrayList<Ride> getAllRides(RecyclerAdapter myAdapter, ArrayList<Ride>rideList) {
        //ArrayList<Ride> rideList = new ArrayList<>();

        // get a Firebase DB instance reference
        DatabaseReference myRef = database.getReference("rides");
        Log.d(DEBUG_TAG, "getAllRides called");

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
                myAdapter.notifyDataSetChanged();
                Log.d(DEBUG_TAG, "rideList size async: " + String.valueOf(rideList.size()));
            } // onDataChange()

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( DEBUG_TAG,"ValueEventListener: reading failed: " + databaseError.getMessage() );
            } // onCancelled()
        } ); // DatabaseReference.addValueEventListener()
        Log.d(DEBUG_TAG, "rideList size return: " + String.valueOf(rideList.size()));
        return rideList;
    } //getRide

    /**
     * Updates the point total for the specified User in the Firebase.
     * @param userID A String representing the ID of the User.
     * @param changeAmount An int representing the amount to change the point total by.
     *                     Can be positive or negative.
     */
    public static void updateUserPoints(String userID, int changeAmount) {
        //Log.d( DEBUG_TAG, "Updating User: " + user.getEmail() + " " + user.getId() );
        Log.d( DEBUG_TAG, "Updating User: " + userID );

        // Update the recycler view to show the changes in the updated job lead in that view
        //recyclerAdapter.notifyItemChanged( position );

        // Update this job lead in Firebase
        // Note that we are using a specific key (one child in the list)
        DatabaseReference ref = database.getReference().child( "users" );

        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        ref.orderByChild("id").equalTo(userID).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                for( DataSnapshot postSnapshot: dataSnapshot.getChildren() ) {
                    // Alternate way to do it. This gets the old point total from the firebase.
                    // That way, we don't have to read pointTotal from UI and pass it in.
                    int oldPointTotal = postSnapshot.getValue(User.class).getPoints();
                    int newPointTotal = oldPointTotal + changeAmount;
                    //
                    // Update User in Firebase
                    postSnapshot.getRef().child("points").setValue(newPointTotal);
                    //Log.d( DEBUG_TAG, "user:" + user.getName() + " newPointTotal:" + String.valueOf(user.getPoints()) );
                    Log.d( DEBUG_TAG, "user:" + userID + " newPointTotal:" + String.valueOf(newPointTotal) );
                } // for every element in firebase
                /*
                dataSnapshot.getRef().setValue( user ).addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d( DEBUG_TAG, "updated ride: " + user.getEmail() + " " + user.getId() );
                        //Toast.makeText(getApplicationContext(), "Job lead updated for ", Toast.LENGTH_SHORT).show();
                    } // onSuccess()
                }); // onSuccessListener()
                 */
            } // onDataChange()

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                //Log.d( DEBUG_TAG, "failed to update user: " + user.getName() );
                Log.d( DEBUG_TAG, "failed to update user: " + userID );
                //Toast.makeText(getApplicationContext(), "Failed to update ride", Toast.LENGTH_SHORT).show();
            } // onCancelled()
        }); // DatabaseReference ref.addListenerForSingleValueEvent
    } // updateUser()

    public static ArrayList<Ride> getAllOffers(RecyclerAdapter myAdapter, ArrayList<Ride>rideList) {
        //ArrayList<Ride> rideList = new ArrayList<>();

        // get a Firebase DB instance reference
        DatabaseReference myRef = database.getReference("rides");
        Log.d(DEBUG_TAG, "getAllRides called");

        // Set up a listener (event handler) to receive a value for the database reference.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method
        // and then each time the value at Firebase changes.
        //
        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Get reference to FirebaseAuth for info about current user
                FirebaseAuth mFirebaseAuth;
                mFirebaseAuth = FirebaseAuth.getInstance();

                // Once we have a DataSnapshot object, we need to iterate over the elements and place them in a list.
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey( postSnapshot.getKey() );

                    // if ride has all fields, add to our local list
                    //
                    if( !(ride.getDriver().equals("")) && !ride.getDriver().equals(mFirebaseAuth.getCurrentUser().getUid()) && (ride.getRider().equals(""))){
                        rideList.add( ride );
                        Log.d( DEBUG_TAG, "ValueEventListener: added: " + ride );
                        //Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                        Log.d(DEBUG_TAG, "");
                    } // if ride has all fields
                } // for every element in firebase
                // Update RecyclerView in UI
                myAdapter.notifyDataSetChanged();
                Log.d(DEBUG_TAG, "rideList size async: " + String.valueOf(rideList.size()));
            } // onDataChange()

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( DEBUG_TAG,"ValueEventListener: reading failed: " + databaseError.getMessage() );
            } // onCancelled()
        } ); // DatabaseReference.addValueEventListener()
        Log.d(DEBUG_TAG, "rideList size return: " + String.valueOf(rideList.size()));
        return rideList;
    } //getRide


    public static ArrayList<Ride> getAllRequests(RecyclerAdapter myAdapter, ArrayList<Ride>rideList) {
        //ArrayList<Ride> rideList = new ArrayList<>();

        // get a Firebase DB instance reference
        DatabaseReference myRef = database.getReference("rides");
        Log.d(DEBUG_TAG, "getAllRides called");

        // Set up a listener (event handler) to receive a value for the database reference.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method
        // and then each time the value at Firebase changes.
        //
        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Get reference to FirebaseAuth for info about current user
                FirebaseAuth mFirebaseAuth;
                mFirebaseAuth = FirebaseAuth.getInstance();

                // Once we have a DataSnapshot object, we need to iterate over the elements and place them in our list
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey( postSnapshot.getKey() );

                    // if ride has all fields, add to our local list
                    //TODO: after reserving a ride an error happens here?
                    if( (ride.getDriver().equals("")) && !ride.getRider().equals(mFirebaseAuth.getCurrentUser().getUid())&& !(ride.getRider().equals(""))){
                        rideList.add( ride );
                        Log.d( DEBUG_TAG, "ValueEventListener: added: " + ride );
                        //Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                        Log.d(DEBUG_TAG, "");
                    } // if ride has all fields, add to our local list
                } // for every element in firebase
                /* These next two lines update the UI. RN, this method just gets the list of rides. We can handle UI here, or somewhere else. */
                //Log.d( DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter" );
                myAdapter.notifyDataSetChanged();
                Log.d(DEBUG_TAG, "rideList size async: " + String.valueOf(rideList.size()));
            } // onDataChange()

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( DEBUG_TAG,"ValueEventListener: reading failed: " + databaseError.getMessage() );
            } // onCancelled()
        } ); // DatabaseReference.addValueEventListener()
        Log.d(DEBUG_TAG, "rideList size return: " + String.valueOf(rideList.size()));
        return rideList;
    } //getRide



    public static ArrayList<Ride> getAllYourRides(RecyclerAdapterYourRides myAdapter, ArrayList<Ride>rideList) {
        //ArrayList<Ride> rideList = new ArrayList<>();

        // get a Firebase DB instance reference
        DatabaseReference myRef = database.getReference("rides");
        Log.d(DEBUG_TAG, "getAllRides called");

        // Set up a listener (event handler) to receive a value for the database reference.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method
        // and then each time the value at Firebase changes.
        //
        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Get reference to FirebaseAuth for info about current user
                FirebaseAuth mFirebaseAuth;
                mFirebaseAuth = FirebaseAuth.getInstance();

                // Once we have a DataSnapshot object, we need to iterate over the elements and place them in a list.
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey( postSnapshot.getKey() );

                    // if ride has all fields, add to our local list
                    //
                    if( (ride.getDriver().equals(mFirebaseAuth.getCurrentUser().getUid())) || (ride.getRider().equals(mFirebaseAuth.getCurrentUser().getUid()))){


                        if (!(ride.isRiderConfirmed() && ride.isDriverConfirmed())) {
                            rideList.add( ride );
                            Log.d( DEBUG_TAG, "ValueEventListener: added: " + ride );
                            //Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                            Log.d(DEBUG_TAG, "");
                        }

                    } // if ride has all fields
                } // for every element in firebase
                // Update RecyclerView in UI
                myAdapter.notifyDataSetChanged();
                Log.d(DEBUG_TAG, "rideList size async: " + String.valueOf(rideList.size()));
            } // onDataChange()

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( DEBUG_TAG,"ValueEventListener: reading failed: " + databaseError.getMessage() );
            } // onCancelled()
        } ); // DatabaseReference.addValueEventListener()
        Log.d(DEBUG_TAG, "rideList size return: " + String.valueOf(rideList.size()));
        return rideList;
    } //getRide




    public static ArrayList<Ride> getAllYourConfrimedRides(RecyclerAdapterYourConfrimedRides myAdapter, ArrayList<Ride>rideList) {
        //ArrayList<Ride> rideList = new ArrayList<>();

        // get a Firebase DB instance reference
        DatabaseReference myRef = database.getReference("rides");
        Log.d(DEBUG_TAG, "getAllRides called");

        // Set up a listener (event handler) to receive a value for the database reference.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method
        // and then each time the value at Firebase changes.
        //
        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Get reference to FirebaseAuth for info about current user
                FirebaseAuth mFirebaseAuth;
                mFirebaseAuth = FirebaseAuth.getInstance();

                // Once we have a DataSnapshot object, we need to iterate over the elements and place them in a list.
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey( postSnapshot.getKey() );

                    // if ride has all fields, add to our local list
                    //
                    if( (ride.getDriver().equals(mFirebaseAuth.getCurrentUser().getUid())) || (ride.getRider().equals(mFirebaseAuth.getCurrentUser().getUid()))){
                        if (ride.isRiderConfirmed() && ride.isDriverConfirmed()) {
                            rideList.add( ride );
                            Log.d( DEBUG_TAG, "ValueEventListener: added: " + ride );
                            //Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                            Log.d(DEBUG_TAG, "");
                        }

                    } // if ride has all fields
                } // for every element in firebase
                // Update RecyclerView in UI
                myAdapter.notifyDataSetChanged();
                Log.d(DEBUG_TAG, "rideList size async: " + String.valueOf(rideList.size()));
            } // onDataChange()

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( DEBUG_TAG,"ValueEventListener: reading failed: " + databaseError.getMessage() );
            } // onCancelled()
        } ); // DatabaseReference.addValueEventListener()
        Log.d(DEBUG_TAG, "rideList size return: " + String.valueOf(rideList.size()));
        return rideList;
    } //getRide



    /**
     * Edit an existing Ride in the Firebase.
     * @param ride a Java object representing the updated Ride.
     */
    public static void updateRide( Ride ride ) {
        Log.d( DEBUG_TAG, "Updating Ride: " + ride.getKey() );

        // Update the recycler view to show the changes in the updated job lead in that view
        //recyclerAdapter.notifyItemChanged( position );

        // Update this job lead in Firebase
        // Note that we are using a specific key (one child in the list)
        DatabaseReference ref = database
                .getReference()
                .child( "rides" )
                .child( ride.getKey() );

        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        ref.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                dataSnapshot.getRef().setValue( ride ).addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d( DEBUG_TAG, "updated ride: " + ride.getKey() );
                        //Toast.makeText(getApplicationContext(), "Job lead updated for ", Toast.LENGTH_SHORT).show();
                    } // onSuccess()
                }); // onSuccessListener()
            } // onDataChange()

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( DEBUG_TAG, "failed to update ride: " + ride.getKey() );
                //Toast.makeText(getApplicationContext(), "Failed to update ride", Toast.LENGTH_SHORT).show();
            } // onCancelled()
        }); // DatabaseReference ref.addListenerForSingleValueEvent
    } // updateRide()

    public static void deleteRide (Ride ride) {
        Log.d( DEBUG_TAG, "Deleting ride: " + ride.getKey() );

        // Delete this job lead in Firebase.
        // Note that we are using a specific key (one child in the list)
        DatabaseReference ref = database
                .getReference()
                .child( "rides" )
                .child( ride.getKey() );

        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        ref.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                dataSnapshot.getRef().removeValue().addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d( DEBUG_TAG, "ride deleted: " + ride.getKey() );
                        //Toast.makeText(getApplicationContext(), "ride deleted: " + ride.getKey(), Toast.LENGTH_SHORT).show();
                    } // onSuccess()
                }); // addOnSuccessListener()
            } // onDataChange()

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( DEBUG_TAG, "failed to delete ride: " + ride.getKey() );
                //Toast.makeText(getApplicationContext(), "Failed to delete ride", Toast.LENGTH_SHORT).show();
            } // onCancelled()
        }); // DatabaseReference ref.addListenerForSingleValueEvent
    } // deleteRide()

    /*
    NOT WORKING
     */
    public static ArrayList<User> getAllUsers( ArrayList<User>rideList) {
        //ArrayList<Ride> rideList = new ArrayList<>();

        // get a Firebase DB instance reference
        DatabaseReference myRef = database.getReference("rides");
        Log.d(DEBUG_TAG, "getAllRides called");

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
                    User ride = postSnapshot.getValue(User.class);
                    ride.setId( postSnapshot.getKey() );
                    rideList.add( ride );
                    Log.d( DEBUG_TAG, "ValueEventListener: added: " + ride );
                    //Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                    Log.d(DEBUG_TAG, "");
                } // for every element in firebase
                /* These next two lines update the UI. RN, this method just gets the list of rides. We can handle UI here, or somewhere else. */
                //Log.d( DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter" );
                Log.d(DEBUG_TAG, "rideList size async: " + String.valueOf(rideList.size()));
            } // onDataChange()

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( DEBUG_TAG,"ValueEventListener: reading failed: " + databaseError.getMessage() );
            } // onCancelled()
        } ); // DatabaseReference.addValueEventListener()
        Log.d(DEBUG_TAG, "rideList size return: " + String.valueOf(rideList.size()));
        return rideList;
    } //getRide

} // FirebaseUtil
