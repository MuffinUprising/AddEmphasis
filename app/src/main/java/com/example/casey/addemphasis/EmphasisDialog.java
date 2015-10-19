package com.example.casey.addemphasis;


/**
 * Created by casey on 10/11/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EmphasisDialog extends DialogFragment implements DialogListener{

    String newPhrase;

    //instantiate listener and attach
    DialogListener listener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        //set the activity to be a listener
        try{
            listener = (DialogListener) activity;
        }catch (ClassCastException cce) {
            throw new ClassCastException("Dialog host must implement SimpleDialogListener");
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //get new phrase from bundle
        newPhrase = getArguments().getString("emphasizedPhrase");

        //build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.result_title).setMessage(R.string.result_msg);

        //set up text view with emphasized text
        final TextView tv = new TextView(getContext());
        builder.setView(tv);
        //formatting
        tv.setTextSize(20);
        tv.setGravity(1);
        //set text to new phrase
        tv.setText(newPhrase);

        builder.setPositiveButton(R.string.result_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "All done", Toast.LENGTH_SHORT).show();
                //notify listener
                listener.onResultOk(EmphasisDialog.this);

            }

        });

        return builder.create();
    }

    @Override
    public void onListOk(ArrayList<Integer> selected) {
        //not used here
    }

    //dismiss dialog after clicking ok
    public void onResultOk(DialogFragment dialog){
        dialog.dismiss();
    }

    @Override
    public void onCancel(DialogFragment dialog) {
        //not used here
    }

}
