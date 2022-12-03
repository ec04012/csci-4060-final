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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    Context context;
    static ArrayList<Ride> rideArrayList;
     static Ride ride;

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
        ride =  rideArrayList.get(position);
        holder.rideIdview.setText(ride.getRideId());
        holder.rideIdview.setText(ride.getRideId());
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

        TextView rideDestCity, rideDestState, rideSourceState, rideSourceCity, rideCar, rideDate, carTitle,rideIdview;
        Button reserve;
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
            reserve = itemView.findViewById(R.id.reserve);


            reserve.setOnClickListener(new View.OnClickListener() {
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

                        //meaning the list of ride's rid matches what we clicked
                        if(fbRideList.get(i).getRideId() == rideIdview.getText()) {
                            reserveRide = fbRideList.get(i);

                            Toast.makeText(view.getContext(), "resrveRide rid = " + reserveRide.getRideId(), Toast.LENGTH_SHORT).show();

                            //meaning its a request
                            if (reserveRide.getDriver() == "") {
                                Toast.makeText(view.getContext(), "set a new driver ", Toast.LENGTH_SHORT).show();
                                reserveRide = fbRideList.get(i);
                                reserveRide.setDriver(mFirebaseAuth.getCurrentUser().getUid());

                                Toast.makeText(view.getContext(), reserveRide.toString(), Toast.LENGTH_LONG).show();
                            }

                            //meaning this is a offer
                            else {
                                Toast.makeText(view.getContext(), "set a new rider ", Toast.LENGTH_SHORT).show();
                                reserveRide.setRider(mFirebaseAuth.getCurrentUser().getUid());
                                Toast.makeText(view.getContext(), reserveRide.toString(), Toast.LENGTH_LONG).show();
                            }




                        }

                    }


                    //bad way but simple fix atm we pass the ride's id from databse here and hide it from user and store into rideId this way we know what the ride id is

                    //Toast.makeText(itemView.getContext(), "rideSourceC" + rideSourceCity.getText() + "rideDEstC" + rideDestCity.getText(), Toast.LENGTH_SHORT).show();

                    /*
                    Intent myIntent = new Intent(view.getContext(), editRideActivity.class);

                    //here we pass the rideId and from here in theory we can do a search for where the rid matches in the database and edit from there.
                    myIntent.putExtra("rid", rideIdview.getText()); //Optional parameters
                    view.getContext().startActivity(myIntent);

                     */


                }
            });

        }
    }




}
