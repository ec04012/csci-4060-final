package edu.uga.cs.ridesharefirebase;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfferRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestRideFragment extends Fragment implements LocationListener, DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    int day = 0;
    int month = 0;
    int year = 0;
    int hour = 0;
    int minuite = 0;

    int savedDay = 0;
    int Savedmonth = 0;
    int Savedyear = 0;
    int Savedhour = 0;
    int Savedminuite = 0;

    // Instantiate View variables
    private EditText destCity, destState, startState, startCity,  dateView;
    private CalendarView calendarView;
    private Button  offerSubmit;
    private String fromState, fromCity, date;
    private Button gpsButton, gpsButton2, datePicker;
    FirebaseAuth mFirebaseAuth;

    LocationManager locationManager;

    public RequestRideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestRideFragment newInstance(String param1, String param2) {
        RequestRideFragment fragment = new RequestRideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        } // if
    } // OfferRideFragment.onCreate()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_ride, container, false);
    } // OfferRideFragment.onCreateView()

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get references to Views
        destCity = view.findViewById(R.id.destCity);
        destState = view.findViewById(R.id.destState);
        startCity = view.findViewById(R.id.startCity);
        startState = view.findViewById(R.id.startState);
        //offerCar = view.findViewById(R.id.offerCar);
        //calendarView = view.findViewById(R.id.calendarView);
        offerSubmit = view.findViewById(R.id.offerSubmit);
        gpsButton = view.findViewById(R.id.gpsButton);
        gpsButton2 = view.findViewById(R.id.gpsButton2);
        datePicker = view.findViewById(R.id.datePicker);
        dateView = view.findViewById(R.id.editTextDate);

        //if the user clicks the gps button under the starting destination
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grantPermission();
                checkLocationIsEnabledOrNot();
                getLocation();
                startCity.setText(fromCity);
                startState.setText(fromState);
            }
        });

        //if the user clicks the gps button under the destination
        gpsButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grantPermission();
                checkLocationIsEnabledOrNot();
                getLocation();
                destCity.setText(fromCity);
                destState.setText(fromState);
            }
        });

        //if the user clicks the date picker
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        date= (i1 + 1) + "/" + i2 + "/" + i;
                        //getDateTimeCalendar();
                        dateView.setText(date);
                    }
                }, year, month, day);

                datePickerDialog.getDatePicker().setMinDate((System.currentTimeMillis()-1000));
                datePickerDialog.show();
            }
        });





        // set listener for submit button
        offerSubmit.setOnClickListener( new SubmitClickListener() );





    } // OfferRideFragment.onViewCreated()


    //gets the location
    private void getLocation() {
        try {
            locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500, 5 , (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    //checks to see if location setting is enabled
    private void checkLocationIsEnabledOrNot() {
        LocationManager lm = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try{
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        }catch (Exception e ) {
            e.printStackTrace();
        }

        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Enable GPS Service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //this intent redirect us to the location settings when the user has gps disabled
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("cancel", null).show();

        }
    }


    //asks permission
    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {

            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }


    //on location change
    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(this.getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            fromCity = addresses.get(0).getLocality();
            fromState = addresses.get(0).getAdminArea();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }



    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {

    }



    //when the user clicks submit do a simple if check
    private class SubmitClickListener implements View.OnClickListener {
        /**
         * A class that listens for when the user clicks the submit button.
         * It checks whether
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            /* TODO: See if we can make this cleaner. Maybe we can do it in XML?
            We probably have to add some fields to the form, and using an if statement
            makes it hard to do that.
            */
            if(TextUtils.isEmpty(startCity.getText().toString()) ) {
                startCity.setError("Please type in the City you are starting from.");
                return;
            }
            if(TextUtils.isEmpty(startState.getText().toString()) ) {
                startState.setError("Please type in the State you are starting from.");
                return;
            }
            if(TextUtils.isEmpty(destState.getText().toString()) ) {
                destState.setError("Please type in the state you are traveling to .");
                return;
            }
            if(TextUtils.isEmpty(destCity.getText().toString()) ) {
                destCity.setError("Please type in the city you are traveling to.");
                return;
            }
            /*
            if(TextUtils.isEmpty(offerCar.getText().toString()) ) {
                offerCar.setError("Please type in a car description");
                return;
            }

             */
            if(TextUtils.isEmpty(dateView.getText().toString()) ) {
                dateView.setError("Please type in a date or use the button below");
                return;
            }
            else {
                //Toast.makeText(getActivity(), "Everything is good", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), "City: " + offerCity.getText().toString() + "\n"+  offerState.getText().toString() + "\n" + "Car: " + offerCar.getText().toString() + "\n" + "State: "  , Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), "From City:\n " + fromCity + "\nFrom State:  \n " + fromState, Toast.LENGTH_LONG).show();
                addRideToFirebase();
            } // if-else
        } // SubmitClickListener.onClick()
    } // SubmitClickListener

    /**
     * Takes the data from the EditTexts, constructs a Ride object, and adds to database.
     */
    private void addRideToFirebase() {

        // Get references to Views
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String destcityString = destCity.getText().toString();
        String deststateString = destState.getText().toString();
        String sourcecityString = startCity.getText().toString();
        String sourcestateString = startState.getText().toString();
        //String car = offerCar.getText().toString();


        // Create Ride Object
        Ride newRide = new Ride();
        newRide.setCar("");
        newRide.setDestinationCity(destcityString);
        newRide.setDestinationState(deststateString);
        newRide.setSourceCity(sourcecityString);
        newRide.setSourceState(sourcestateString);
        newRide.setDate(date);
        mFirebaseAuth = FirebaseAuth.getInstance();
        newRide.setDriver("");
        newRide.setRider(mFirebaseAuth.getCurrentUser().getUid());

        //when its added it would be
        /*
        TODO: methods to implment below these should work just need the database to be updated to take these info.
        newRide.setDate(date);
        newRide.setDriver = mFirebaseAuth.getCurrentUser().getUid();
         */

        // Add a new element (Ride) to the list of job leads in Firebase.

        DatabaseReference myRef = database.getReference("rides");

        // First, a call to push() appends a new node to the existing list (one is created
        // if this is done for the first time).  Then, we set the value in the newly created
        // list node to store the new job lead.
        // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
        // the previous apps to maintain job leads.
        myRef.push().setValue( newRide )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Show a quick confirmation
                        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
                        Toast.makeText(getActivity().getApplicationContext(), "Ride Created" + "UID = " + mFirebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                        // Clear the EditTexts for next use.
                        //offerCar.setText("");
                        destCity.setText("");
                        destState.setText("");
                        startCity.setText("");
                        startState.setText("");
                        //dateView.setText("");

                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure( @NonNull Exception e ) {
                        Toast.makeText(getActivity().getApplicationContext(), "Failed to create Ride", Toast.LENGTH_SHORT).show();
                    }
                }); // .addOnSuccessListener()
    } // addRideToFirebase()

} // RequestRideFragment