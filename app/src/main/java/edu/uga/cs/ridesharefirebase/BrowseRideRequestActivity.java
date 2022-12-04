package edu.uga.cs.ridesharefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BrowseRideRequestActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference database;
    RecyclerAdapter myAdapter;
    ArrayList<Ride> list;
    private static String DEBUG_TAG = "testreview";
    private ArrayList<Ride> RequestList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_ride_request);

        recyclerView = findViewById(R.id.test);
        database = FirebaseDatabase.getInstance().getReference("rides");
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        myAdapter = new RecyclerAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Ride ride = postSnapshot.getValue(Ride.class);
                    ride.setKey(postSnapshot.getKey());
                    //list.add(ride);
                    Log.d(DEBUG_TAG, "driver = " + ride.getDriver());
                    Log.d(DEBUG_TAG, "there is  no driver bool" + (ride.getDriver().equals("")));

                    Log.d(DEBUG_TAG, "rider = " + ride.getRider());
                    Log.d(DEBUG_TAG, "there is no  rider bool" + (ride.getRider().equals("")));


                    if ((ride.getDriver().equals("")) && !(ride.getRider().equals(""))) {
                        list.add(ride);

                        Log.d(DEBUG_TAG, "ValueEventListener: added: " + ride);
                        //Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                        Log.d(DEBUG_TAG, "");
                    }


                } // for every element in firebase
                /* These next two lines update the UI. RN, this method just gets the list of rides. We can handle UI here, or somewhere else. */
                //Log.d( DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter" );
                myAdapter.notifyDataSetChanged();
            } // onDataChange()

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}