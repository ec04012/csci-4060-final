package edu.uga.cs.ridesharefirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterYourConfrimedRides extends RecyclerView.Adapter<RecyclerAdapterYourConfrimedRides.MyViewHolder> {

    Context context;
    static ArrayList<Ride> rideArrayList;
    static Ride ride;



    public RecyclerAdapterYourConfrimedRides(Context context, ArrayList<Ride> list) {
        this.context = context;
        rideArrayList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v   = LayoutInflater.from(context).inflate(R.layout.list_confirmed_rides, parent,false);
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
        holder.rider.setText("Rider:" + ride.getRiderName());
        holder.driver.setText("Driver: " +ride.getDriverName());
        holder.rideDate.setText(ride.getDate());

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
        //Button editRide, confrimedRide;
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
            //editRide = itemView.findViewById(R.id.editInfomation);
            rider = itemView.findViewById(R.id.rider);
            driver = itemView.findViewById(R.id.driver);
            //confrimedRide = itemView.findViewById(R.id.confirmRide);

        } // MyViewHolder() constructor
    } // MyViewHolder






} // recyclerAdapter
