package edu.uga.cs.ridesharefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        String rideID, rideDriver, rideRider, rideCar, rideDestCity, rideDestState,
                rideStartCity, rideStartState,date, driverName, riderName;
        boolean    riderConfrimed,driverConfirmed;
        Button datePicker, update, delete;

        int day = 0;
        int month = 0;
        int year = 0;

        EditText destStateEdit, destCityEdit, startStateEdit, startCityEdit, dateEdit;
        Intent intent = getIntent();
       // test.setText("wow");

        rideID = intent.getStringExtra("rideID");
        rideDriver = intent.getStringExtra("rideDriver");
        rideRider = intent.getStringExtra("rideRider");
        rideCar = intent.getStringExtra("rideCar");
        rideDestCity = intent.getStringExtra("rideDestCity");
        rideDestState = intent.getStringExtra("rideDestState");
        rideStartCity = intent.getStringExtra("rideStartCity");
        rideStartState = intent.getStringExtra("rideStartState");
        riderConfrimed = Boolean.parseBoolean(intent.getStringExtra("riderConfrimed"));
        driverConfirmed = Boolean.parseBoolean(intent.getStringExtra("driverConfirmed"));
        date = intent.getStringExtra("date");
        driverName = intent.getStringExtra("driverName");
        riderName = intent.getStringExtra("riderName");


        Ride editRide = new Ride();
        editRide.setKey(rideID);
        editRide.setDriver(rideDriver);
        editRide.setRider(rideRider);
        editRide.setDestinationCity(rideDestCity);
        editRide.setDestinationState(rideDestState);
        editRide.setCar(rideCar);
        editRide.setSourceState(rideStartState);
        editRide.setSourceCity(rideStartCity);
        editRide.setRiderConfirmed(riderConfrimed);
        editRide.setDriverConfirmed(driverConfirmed);
        editRide.setDate(date);
        editRide.setKey(rideID);
        editRide.setDriverName(driverName);
        editRide.setRiderName(riderName);

        Toast.makeText(this, editRide.toString(), Toast.LENGTH_LONG).show();


        //test.setText(rideID);
        destStateEdit = findViewById(R.id.destStateEdit);
        destCityEdit = findViewById(R.id.destCityEdit);
        startStateEdit = findViewById(R.id.startStateEdit);
        startCityEdit = findViewById(R.id.startCityEdit);
        dateEdit = findViewById(R.id.editTextDateEdit);
        datePicker = findViewById(R.id.datePickerEdit);
        delete = findViewById(R.id.deleteRide);
        update = findViewById(R.id.update);



      destStateEdit.setText(editRide.getDestinationState());
      destCityEdit.setText(editRide.getDestinationCity());
      startStateEdit.setText(editRide.getSourceState());
      startCityEdit.setText(editRide.getSourceCity());
        dateEdit.setText(editRide.getDate());

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditRideActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String dateString= (i1 + 1) + "/" + i2 + "/" + i;
                        //getDateTimeCalendar();
                        dateEdit.setText(dateString);
                    }
                }, year, month, day);

                datePickerDialog.getDatePicker().setMinDate((System.currentTimeMillis()-1000));
                datePickerDialog.show();

                //Toast.makeText(EditRideActivity.this, "clicked button", Toast.LENGTH_SHORT).show();
            } // .onClick()
        }); // datePicker.setOnClickListener()

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditRideActivity.this, "clicked update", Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(startCityEdit.getText().toString()) ) {
                    startCityEdit.setError("Please type in the City you are starting from.");
                    return;
                }
                if(TextUtils.isEmpty(startStateEdit.getText().toString()) ) {
                    startStateEdit.setError("Please type in the State you are starting from.");
                    return;
                }
                if(TextUtils.isEmpty(destStateEdit.getText().toString()) ) {
                    destStateEdit.setError("Please type in the state you are traveling to .");
                    return;
                }
                if(TextUtils.isEmpty(destCityEdit.getText().toString()) ) {
                    destCityEdit.setError("Please type in the city you are traveling to.");
                    return;
                }

                if(TextUtils.isEmpty(dateEdit.getText().toString()) ) {
                    dateEdit.setError("Please type in a date or use the button below");
                    return;
                }
                else {
                    Toast.makeText(EditRideActivity.this, "Update your ride", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), "City: " + offerCity.getText().toString() + "\n"+  offerState.getText().toString() + "\n" + "Car: " + offerCar.getText().toString() + "\n" + "State: "  , Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), "From City:\n " + fromCity + "\nFrom State:  \n " + fromState, Toast.LENGTH_LONG).show();
                    editRide.setDate(dateEdit.getText().toString());
                    editRide.setSourceCity(startCityEdit.getText().toString());
                    editRide.setSourceState(startStateEdit.getText().toString());
                    editRide.setDestinationState(destStateEdit.getText().toString());
                    editRide.setDestinationCity(destCityEdit.getText().toString());
                    FirebaseUtil.updateRide(editRide);
                    finish();


                } // if-else

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditRideActivity.this, "deleted your ride", Toast.LENGTH_SHORT).show();
                FirebaseUtil.deleteRide(editRide);
                finish();
            }
        });




       // FirebaseUtil.updateRide(ride);



    }



}