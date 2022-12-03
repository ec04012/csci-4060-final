package edu.uga.cs.ridesharefirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Ride> rideArrayList;

    public recyclerAdapter(Context context, ArrayList<Ride> list) {
        this.context = context;
        rideArrayList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v   = LayoutInflater.from(context).inflate(R.layout.list_rides, parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ride ride =  rideArrayList.get(position);
        holder.rideSourceCity.setText(ride.getSourceCity());
        holder.rideSourceState.setText(ride.getSourceState());
        holder.rideDestCity.setText(ride.getDestinationCity());
        holder.rideDestState.setText(ride.getDestinationState());
        //holder.rideDate.setText(ride.getDate());

        //bascially a tsudo check to see if the ride is an request since they dont need a car
        // would instead on final check to see if driver is null/false/no UID
        if(ride.getCar() == "") {
            holder.carTitle.setVisibility(View.INVISIBLE);
            holder.rideCar.setVisibility(View.INVISIBLE);
        } else {
            holder.rideCar.setText(ride.getCar());
        }



    }

    @Override
    public int getItemCount() {
        return rideArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView rideDestCity, rideDestState, rideSourceState, rideSourceCity, rideCar, rideDate, carTitle;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rideSourceCity = itemView.findViewById(R.id.rideSourceCity);
            rideDestCity = itemView.findViewById(R.id.rideDestCity);
            rideDestState = itemView.findViewById(R.id.rideDestState);
            rideSourceState = itemView.findViewById(R.id.rideSourceState);
            rideCar = itemView.findViewById(R.id.rideCar);
            carTitle = itemView.findViewById(R.id.carTitle);
            rideDate = itemView.findViewById(R.id.rideDate);


        }
    }

}
