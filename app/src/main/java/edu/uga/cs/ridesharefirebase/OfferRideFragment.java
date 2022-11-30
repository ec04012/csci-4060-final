package edu.uga.cs.ridesharefirebase;

import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfferRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfferRideFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offer_ride, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText offerCity, offerState, offerCar;
        CalendarView calendarView;
        Button  offerSubmit;
        String cityString, stateString, carString;




        offerCity = view.findViewById(R.id.offerCity);
        offerState = view.findViewById(R.id.offerState);
        offerCar = view.findViewById(R.id.offerCar);
        calendarView = view.findViewById(R.id.calendarView);
        offerSubmit = view.findViewById(R.id.offerSubmit);







        calendarView.setMinDate(System.currentTimeMillis()-1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date= (i1 +1) + "/" + i2+ "/" + i;
                Toast.makeText(view.getContext(), date, Toast.LENGTH_SHORT).show();
            }
        });


        offerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    Toast.makeText(view.getContext(), "Everything is good", Toast.LENGTH_SHORT).show();
                    Toast.makeText(view.getContext(), "City: " + offerCity.getText().toString() + "\n"+  offerState.getText().toString() + "\n" + "Car: " + offerCar.getText().toString() + "\n" + "State: "  , Toast.LENGTH_SHORT).show();


                }

            }
        });








    }


}