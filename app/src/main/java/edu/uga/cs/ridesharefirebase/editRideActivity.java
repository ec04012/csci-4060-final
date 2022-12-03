package edu.uga.cs.ridesharefirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class editRideActivity extends AppCompatActivity {
    Ride editingRide;
    ArrayList<Ride> arrayRideList = new ArrayList<Ride>();
    private EditText editdestCity, editdestState, editstartState, editstartCity, editofferCar, editdateView;
    private CalendarView calendarView;
    private Button offerSubmit;
    private String editfromState, editfromCity, date;
    private Button gpsButton, gpsButton2, datePicker;
    FirebaseAuth mFirebaseAuth;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ride);

        Intent intent = getIntent();
        String rideId = intent.getStringExtra("rid");
        TextView editRid;
        editRid = this.findViewById(R.id.editrid);
        editRid.setText(rideId);




        //do somekind of initalization from db get the and set to arrayRideList
        dataInitialize();

        //do some kind of iteration of the list to check the ride id
        for (int i = 0 ; i < arrayRideList.size(); i++) {

            //if the ride id of the list of rides matches the rid of the one we selected
            if (arrayRideList.get(i).getRideId() == rideId) {
                editingRide = arrayRideList.get(i);
                Toast.makeText(this, editingRide.getDestinationCity(), Toast.LENGTH_SHORT).show();
            }
        }

/*
        editdestCity = this.findViewById(R.id. editdestCity);
        editdestState = this.findViewById(R.id. editdestState);
        editstartCity = this.findViewById(R.id. editstartCity);
        editstartState = this.findViewById(R.id. editstartState);
        editofferCar = this.findViewById(R.id. editofferCar);
        //calendarView = view.findViewById(R.id.calendarView);
        offerSubmit = this.findViewById(R.id.offerSubmit);
        gpsButton = this.findViewById(R.id.gpsButton);
        gpsButton2 = this.findViewById(R.id.gpsButton2);
        datePicker = this.findViewById(R.id. editdatePicker);
        editdateView = this.findViewById(R.id. editeditTextDate);


        editdestCity.setText(editingRide.getDestinationCity());
        editdestState.setText(editingRide.getDestinationState());
        editstartCity.setText(editingRide.getSourceCity());
        editstartState.setText(editingRide.getSourceState());
        editofferCar.setText(editingRide.getCar());

 */











    }


    private void dataInitialize() {
        arrayRideList = new ArrayList<Ride>();



        Ride rideOffer = new Ride();
        Ride rideRequest = new Ride();
        Ride rideOffer2 = new Ride();
        Ride rideRequest2 = new Ride();

        rideOffer.setRideId("1");
        rideOffer.setSourceCity("Athens");
        rideOffer.setSourceState("Georgia");
        rideOffer.setDestinationCity("Atlanta");
        rideOffer.setDestinationState("Georgia");
        rideOffer.setCar("Black Audi A4");
        rideOffer.setDate("12/1/2022");

        rideOffer2.setRideId("2");
        rideOffer2.setSourceCity("New York City");
        rideOffer2.setSourceState("New York");
        rideOffer2.setDestinationCity("Atlanta");
        rideOffer2.setDestinationState("Georgia");
        rideOffer2.setCar("Black Audi A4");
        rideOffer2.setDate("12/1/2022");

        rideRequest.setRideId("3");
        rideRequest.setSourceCity("Source State REquest ");
        rideRequest.setSourceState("Source Georgia");
        rideRequest.setDestinationCity("Source Atlanta");
        rideRequest.setDestinationState("Source Georgia");
        rideRequest.setCar("");
        rideRequest.setDate("12/1/2022");

        rideRequest2.setRideId("4");
        rideRequest2.setSourceCity("Source State REquest2  ");
        rideRequest2.setSourceState("Source Georgia 2");
        rideRequest2.setDestinationCity("Source Atlanta 2 ");
        rideRequest2.setDestinationState("Source Georgia 2 ");
        rideRequest2.setCar("");
        rideRequest2.setDate("12/1/2022");

        arrayRideList.add(rideOffer);
        arrayRideList.add(rideOffer2);
        arrayRideList.add(rideRequest);
        arrayRideList.add(rideRequest2);


    }
}