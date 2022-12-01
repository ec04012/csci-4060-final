package edu.uga.cs.ridesharefirebase;

import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfferRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfferRideFragment extends Fragment implements LocationListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Instantiate View variables
    private EditText offerCity, offerState, offerCar;
    private CalendarView calendarView;
    private Button  offerSubmit;
    private String fromState, fromCity, date;

    LocationManager locationManager;

    public OfferRideFragment() {
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
    public static OfferRideFragment newInstance(String param1, String param2) {
        OfferRideFragment fragment = new OfferRideFragment();
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
        return inflater.inflate(R.layout.fragment_offer_ride, container, false);
    } // OfferRideFragment.onCreateView()

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get references to Views
        grantPermission();
        offerCity = view.findViewById(R.id.offerCity);
        offerState = view.findViewById(R.id.offerState);
        offerCar = view.findViewById(R.id.offerCar);
        calendarView = view.findViewById(R.id.calendarView);
        offerSubmit = view.findViewById(R.id.offerSubmit);
        checkLocationIsEnabledOrNot();
        getLocation();


        // Setup calendar select view
        calendarView.setMinDate(System.currentTimeMillis()-1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date= (i1 + 1) + "/" + i2 + "/" + i;
                Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
            } // onSelectedDayChange()
        }); // calendarView.setOnDateChangeListener()

        // set listener for submit button
        offerSubmit.setOnClickListener( new SubmitClickListener() );
    } // OfferRideFragment.onViewCreated()

    private void getLocation() {
        try {
            locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500, 5 , (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

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



    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
      ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {

            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

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
            if(TextUtils.isEmpty(offerCity.getText().toString()) ) {
                offerCity.setError("Please type in the City you are traveling to.");
                return;
            }
            if(TextUtils.isEmpty(offerState.getText().toString()) ) {
                offerState.setError("Please type in the City you are traveling to.");
                return;
            }
            if(TextUtils.isEmpty(offerCar.getText().toString()) ) {
                offerCar.setError("Please type in the City you are traveling to.");
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
        String city = offerCity.getText().toString();
        String state = offerState.getText().toString();
        String car = offerCar.getText().toString();

        // Create Ride Object
        Ride newRide = new Ride();
        newRide.setDestinationCity(city);
        newRide.setDestinationState(state);
        newRide.setCar(car);
        newRide.setSourceState(fromState);
        newRide.setSourceCity(fromCity);

        // Add a new element (Ride) to the list of job leads in Firebase.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
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
                    Toast.makeText(getActivity().getApplicationContext(), "Ride Created", Toast.LENGTH_SHORT).show();
                    // Clear the EditTexts for next use.
                    offerCar.setText("");
                    offerCity.setText("");
                    offerState.setText("");
                }
            })
            .addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure( @NonNull Exception e ) {
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to create Ride", Toast.LENGTH_SHORT).show();
                }
            }); // .addOnSuccessListener()
    } // addRideToFirebase()

} // OfferRideFragment