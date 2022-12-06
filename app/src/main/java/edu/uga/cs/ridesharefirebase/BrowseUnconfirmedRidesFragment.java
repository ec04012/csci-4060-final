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
 * Use the {@link BrowseUnconfirmedRidesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowseUnconfirmedRidesFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private RecyclerAdapterYourRides myAdapter;
    private ArrayList<Ride> rideArrayList;
    private ArrayList<Ride> RequestList;

    public BrowseUnconfirmedRidesFragment() {
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
    } // newInstance()

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //dataInitialize();
    } //  onCreate()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse_ride, container, false);
    } // onCreateView()

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclierview);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        /*
        RequestList = new ArrayList<Ride>();
        for (int i = 0 ; i < rideArrayList.size(); i++) {
            //in the future this will be if !DRIVE UID == "" but for now if there is no car it means its a request
            if (!(rideArrayList.get(i).getDriver() == "") && (rideArrayList.get(i).getRider() == "")) {
                RequestList.add(rideArrayList.get(i));

            } // if ride is rider offer
        } // for every ride return from firebase
         */
        rideArrayList = new ArrayList<Ride>();
        myAdapter = new RecyclerAdapterYourRides(getContext(),rideArrayList);
        recyclerView.setAdapter(myAdapter);
        FirebaseUtil.getAllYourRides(myAdapter, rideArrayList);
        //        //myAdapter.notifyDataSetChanged();
    } // onViewCreated()

}