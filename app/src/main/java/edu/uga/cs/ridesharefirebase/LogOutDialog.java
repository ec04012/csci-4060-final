package edu.uga.cs.ridesharefirebase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;

public class LogOutDialog extends AppCompatDialogFragment {
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder((getActivity()));
        builder.setTitle("Log Out")
            .setMessage("Are you Sure you want to log out?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mFirebaseAuth.signOut();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                } // .setPositiveButton().onClick()
            }) // .setPositiveButton()
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                } // .setNegativeButton().onClick()
            }); // .setNegativeButton()
            return builder.create();
    } // onCreateDialog()

} // LogOutDialog()
