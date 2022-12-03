package edu.uga.cs.ridesharefirebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowseRideRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowseRideOfferFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private ArrayList<Ride> rideArrayList;
    private ArrayList<Ride> RequestList;

    public BrowseRideOfferFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrowseRide.
     */
    // TODO: Rename and change types and number of parameters
    public static BrowseRideRequestFragment newInstance(String param1, String param2) {
        BrowseRideRequestFragment fragment = new BrowseRideRequestFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse_ride, container, false);




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        dataInitialize();
        recyclerView = view.findViewById(R.id.recyclierview);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));




        RequestList = new ArrayList<Ride>();
        for (int i = 0 ; i < rideArrayList.size(); i++) {
            //in the future this will be if !DRIVE UID == "" but for now if there is no car it means its a request
            if (!(rideArrayList.get(i).getCar() == "")) {
                RequestList.add(rideArrayList.get(i));

            } else {

            }
        }





        recyclerAdapter myAdapter = new recyclerAdapter(getContext(),RequestList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {
        rideArrayList = new ArrayList<Ride>();


        Ride rideOffer = new Ride();
        Ride rideRequest = new Ride();
        Ride rideOffer2 = new Ride();
        Ride rideRequest2 = new Ride();
        rideOffer.setSourceCity("Athens");
        rideOffer.setSourceState("Georgia");
        rideOffer.setDestinationCity("Atlanta");
        rideOffer.setDestinationState("Georgia");
        rideOffer.setCar("Black Audi A4");

        rideOffer2.setSourceCity("New York City");
        rideOffer2.setSourceState("New York");
        rideOffer2.setDestinationCity("Atlanta");
        rideOffer2.setDestinationState("Georgia");
        rideOffer2.setCar("Black Audi A4");

        rideRequest.setSourceCity("Source State REquest ");
        rideRequest.setSourceState("Source Georgia");
        rideRequest.setDestinationCity("Source Atlanta");
        rideRequest.setDestinationState("Source Georgia");
        rideRequest.setCar("");

        rideRequest2.setSourceCity("Source State REquest2  ");
        rideRequest2.setSourceState("Source Georgia 2");
        rideRequest2.setDestinationCity("Source Atlanta 2 ");
        rideRequest2.setDestinationState("Source Georgia 2 ");
        rideRequest2.setCar("");

        rideArrayList.add(rideOffer);
        rideArrayList.add(rideOffer2);
        rideArrayList.add(rideRequest);
        rideArrayList.add(rideRequest2);

    }
}