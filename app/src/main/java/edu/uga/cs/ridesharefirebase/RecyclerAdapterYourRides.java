package edu.uga.cs.ridesharefirebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RecyclerAdapterYourRides extends RecyclerView.Adapter<RecyclerAdapterYourRides.MyViewHolder> {

    Context context;
    static ArrayList<Ride> rideArrayList;
    static Ride ride;



    public RecyclerAdapterYourRides(Context context, ArrayList<Ride> list) {
        this.context = context;
        rideArrayList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v   = LayoutInflater.from(context).inflate(R.layout.list_your_rides, parent,false);
        return new MyViewHolder(v);
    } // onCreateViewHolder()


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ride =  rideArrayList.get(position);
        holder.rideIdview.setText(ride.getKey());
        holder.rideSourceCity.setText(ride.getSourceCity());
        holder.rideSourceState.setText(ride.getSourceState());
        holder.rideDestCity.setText(ride.getDestinationCity());
        holder.rideDestState.setText(ride.getDestinationState());


        //TODO: replace name;

        holder.rider.setText("Rider:" + ride.getRider());
        holder.driver.setText("Driver: " +ride.getDriver());


        //holder.rideDate.setText(ride.getDate());

        //bascially a pseudo check to see if the ride is an request since they dont need a car
        // would instead on final check to see if driver is null/false/no UID



        if(ride.getCar() == "") {
            holder.carTitle.setVisibility(View.INVISIBLE);
            holder.rideCar.setVisibility(View.INVISIBLE);
        } else {
            holder.rideCar.setText(ride.getCar());
        }


    } // onBindViewHolder()

    @Override
    public int getItemCount() {
        return rideArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView rideDestCity, rideDestState, rideSourceState, rideSourceCity, rideCar, rideDate, carTitle,rideIdview , rider, driver;
        Button editRide, confrimedRide;
        String rideId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rideSourceCity = itemView.findViewById(R.id.rideSourceCity);
            rideIdview = itemView.findViewById(R.id.rideId);
            rideDestCity = itemView.findViewById(R.id.rideDestCity);
            rideDestState = itemView.findViewById(R.id.rideDestState);
            rideSourceState = itemView.findViewById(R.id.rideSourceState);
            rideCar = itemView.findViewById(R.id.rideCar);
            carTitle = itemView.findViewById(R.id.carTitle);
            rideDate = itemView.findViewById(R.id.rideDate);
            editRide = itemView.findViewById(R.id.editInfomation);
            rider = itemView.findViewById(R.id.rider);
            driver = itemView.findViewById(R.id.driver);
            confrimedRide = itemView.findViewById(R.id.confirmRide);
            
            confrimedRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth mFirebaseAuth;
                    mFirebaseAuth = FirebaseAuth.getInstance();
                    ArrayList<Ride> fbRideList = new ArrayList<Ride>();
                    fbRideList = rideArrayList;

                    for (int i = 0 ; i < fbRideList.size(); i++) {
                        if(fbRideList.get(i).getKey() == rideIdview.getText()) {
                            Toast.makeText(view.getContext(), "made it here ride", Toast.LENGTH_SHORT).show();
                            Toast.makeText(view.getContext(), "first logic " + !(fbRideList.get(i).getRider().equals("")) , Toast.LENGTH_SHORT).show();
                            Toast.makeText(view.getContext(), "2nd logic " + !(fbRideList.get(i).getDriver().equals("")) , Toast.LENGTH_SHORT).show();
                            if (!(fbRideList.get(i).getDriver().equals("")) && !(fbRideList.get(i).getRider().equals(""))) {
                                Toast.makeText(view.getContext(), "first logic true", Toast.LENGTH_SHORT).show();


                                //user is the Rider
                                if (fbRideList.get(i).getRider().equals(mFirebaseAuth.getCurrentUser().getUid())) {
                                    fbRideList.get(i).setRiderConfirmed(true);
                                    FirebaseUtil.updateRide(fbRideList.get(i));
                                    //user is the Driver
                                }else {
                                    fbRideList.get(i).setDriverConfirmed(true);
                                    FirebaseUtil.updateRide(fbRideList.get(i));

                                }

                                if (fbRideList.get(i).isDriverConfirmed() && fbRideList.get(i).isRiderConfirmed()) {
                                    FirebaseUtil.updateUserPoints(fbRideList.get(i).getRider(), -50);
                                    FirebaseUtil.updateUserPoints(fbRideList.get(i).getDriver(), 50);
                                }







                            }
                        }//if ride arraylist == id
                    }//iterate through list
                }//on click
            });


            editRide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //logic here would be to get the list from firebase
                    ArrayList<Ride> fbRideList = new ArrayList<Ride>();
                    Ride reserveRide;
                    FirebaseAuth mFirebaseAuth;
                    mFirebaseAuth = FirebaseAuth.getInstance();
                    //initalize it
                    fbRideList = rideArrayList;

                    for (int i = 0 ; i < fbRideList.size(); i++) {

                        if (!(fbRideList.get(i).getRider().equals("")) && !(fbRideList.get(i).getDriver().equals(""))) {
                            //Toast.makeText(view.getContext(), "you cannot edit this ride", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(fbRideList.get(i).getKey() == rideIdview.getText()) {
                                Intent intent = new Intent(view.getContext(), EditRideActivity.class);
                                intent.putExtra("rideID", fbRideList.get(i).getKey());
                                intent.putExtra("rideDriver", fbRideList.get(i).getDriver());
                                intent.putExtra("rideRider", fbRideList.get(i).getRider());
                                intent.putExtra("rideCar", fbRideList.get(i).getCar());
                                intent.putExtra("rideDestCity", fbRideList.get(i).getDestinationCity());
                                intent.putExtra("rideDestState", fbRideList.get(i).getDestinationState());
                                intent.putExtra("rideStartState", fbRideList.get(i).getSourceState());
                                intent.putExtra("rideStartCity", fbRideList.get(i).getSourceCity());
                                intent.putExtra("riderConfirmed", fbRideList.get(i).isRiderConfirmed());
                                intent.putExtra("driverConfrimed", fbRideList.get(i).isDriverConfirmed());
                                intent.putExtra("date", fbRideList.get(i).getDate());


                                //intent.putExtra("rideID", "Hello");

                                view.getContext().startActivity(intent);

                            }
                        }
                        
                        
                        //meaning the list of ride's rid matches what we clicked


                    }
                    // for every ride


                    //bad way but simple fix atm we pass the ride's id from databse here and hide it from user and store into rideId this way we know what the ride id is

                    //Toast.makeText(itemView.getContext(), "rideSourceC" + rideSourceCity.getText() + "rideDEstC" + rideDestCity.getText(), Toast.LENGTH_SHORT).show();

                    /*
                    Intent myIntent = new Intent(view.getContext(), editRideActivity.class);

                    //here we pass the rideId and from here in theory we can do a search for where the rid matches in the database and edit from there.
                    myIntent.putExtra("rid", rideIdview.getText()); //Optional parameters
                    view.getContext().startActivity(myIntent);

                     */
                } // .onClick()
            }); // reserveButton.setOnClickListener()


        } // MyViewHolder() constructor
    } // MyViewHolder






} // recyclerAdapter
