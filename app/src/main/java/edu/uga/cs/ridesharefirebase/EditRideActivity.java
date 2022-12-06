package edu.uga.cs.ridesharefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditRideActivity extends AppCompatActivity {
    private static String DEBUG_TAG = "FirebaseUtil";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ride2);

        String rideID, rideDriver, rideRider, rideCar, rideDestCity, rideDestState,rideStartCity, rideStartState, riderConfrimed, driverConfirmed;

        TextView test;
        Intent intent = getIntent();
       // test.setText("wow");

         rideID = intent.getStringExtra("rideID");
        //test.setText(rideID);

        Ride ride = new Ride();
        ride.setKey(rideID);
        ride.setDestinationState("UPDATE");

        FirebaseUtil.updateRide(ride);



    }



}