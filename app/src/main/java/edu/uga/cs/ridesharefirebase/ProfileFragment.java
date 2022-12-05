package edu.uga.cs.ridesharefirebase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    String DEBUG_TAG = "ProfileFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button subtractButton;
    private Button addButton;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView pointsTextView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        } // if arguments are not null
    } // onCreate()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    } // ProfileFragment.onCreateView()

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get references to views
        subtractButton = (Button) view.findViewById(R.id.profile_subtract_points_button);
        addButton = (Button) view.findViewById(R.id.profile_add_points_button);
        nameTextView = (TextView) view.findViewById(R.id.profile_fullname);
        emailTextView = (TextView) view.findViewById(R.id.profile_email);
        pointsTextView = (TextView) view.findViewById(R.id.profile_points);

        // Get References to Firebase
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference myRef = database.getReference( "users" );
         FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

         // Read from the database value for ”message”
         myRef.orderByChild("id").equalTo(firebaseUser.getUid()).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot ) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User userPojoTemp = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                Log.d(DEBUG_TAG, userPojoTemp.toString());
                nameTextView.setText( userPojoTemp.getName() );
                emailTextView.setText( userPojoTemp.getEmail() );
                pointsTextView.setText( "Points:" + String.valueOf(userPojoTemp.getPoints()) );
            } // onDataChange()

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d( DEBUG_TAG, " Failed to read value.", error.toException() );
            } // onCancelled()
        }); // addListenerForSingleValueEvent()

        // Create a userPOJO containing the user info currently on screen
        User userPojoMain = new User(firebaseUser);

        // User regex to extract point total from UI
        Pattern stringPattern = Pattern.compile("[0-9]+");

        //if the user clicks the subtractButton under the destination
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use regex to get current point total from the UI
                Matcher m = stringPattern.matcher( pointsTextView.getText().toString() );
                m.find();
                int currentPoints = Integer.parseInt( m.group() );
                // Only subtract points if currentPoints > 0
                if ( currentPoints > 0 ) {
                    // Change point total
                    userPojoMain.setPoints( currentPoints - 50 );
                    // Update firebase (this is asynchronous)
                    FirebaseUtil.updateUserPoints(userPojoMain);
                    // Update textView
                    pointsTextView.setText( "Points:" + String.valueOf(userPojoMain.getPoints()) );
                } else {
                    Toast.makeText( getActivity(), "Can't subtract points.", Toast.LENGTH_SHORT ).show();
                } // if current points > 0 subtract points, else sow toast
            } // .setOnClick()
        }); //subtractButton.setOnClickListener()

        //if the user clicks the subtractButton under the destination
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use regex to get current point total from the UI
                Matcher m = stringPattern.matcher( pointsTextView.getText().toString() );
                m.find();
                int currentPoints = Integer.parseInt( m.group() );
                // Change point total
                userPojoMain.setPoints( currentPoints + 50 );
                // Update firebase (this is asynchronous)
                FirebaseUtil.updateUserPoints(userPojoMain);
                // Update textView
                pointsTextView.setText( "Points:" + String.valueOf(userPojoMain.getPoints()) );
            } // .setOnClick()
        }); //addButton.setOnClickListener()

    } // onViewCreated()

} // ProfileFragment