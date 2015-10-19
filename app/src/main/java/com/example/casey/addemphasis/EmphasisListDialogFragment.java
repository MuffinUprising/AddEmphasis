package com.example.casey.addemphasis;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by casey on 10/11/15.
 */
public class EmphasisListDialogFragment extends DialogFragment implements DialogListener{

    //lists for selected emphasis and options
    private ArrayList<Integer> selectedItems;
    String[] emphasisList;

    //instantiate listener and attach
    DialogListener listener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //set the activity to the listener
        try {
            listener = (DialogListener) activity;
        } catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() + "must implement DialogListener");
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //track selection
        selectedItems = new ArrayList<>();
        //get emphasis choices from bundled string array
        emphasisList = getArguments().getStringArray("emphasisList");

        //build AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.list_title)
                //specify list array, items to be selected by default (null for none)
                //and listener to receive callbacks
                .setMultiChoiceItems(emphasisList, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            //f checked, add to selected items
                            selectedItems.add(which);
                            Toast.makeText(getActivity(), getSelectedItemsAsString(), Toast.LENGTH_SHORT).show();
                        } else if (selectedItems.contains(which)) {
                            //if already in array, remove
                            selectedItems.remove(Integer.valueOf(which));
                            Toast.makeText(getActivity(), getSelectedItemsAsString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setPositiveButton(R.string.list_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //check for emphasis selection
                        if (selectedItems.isEmpty()) {
                            Toast.makeText(getActivity(), "Please select emphasis", Toast.LENGTH_SHORT).show();
                        } else {
                            setSelectedItems(selectedItems);
                            //toast to emphasis
                            Toast.makeText(getActivity(), "Emphasis added", Toast.LENGTH_SHORT).show();
                            //notify listener of selected items
                            listener.onListOk(selectedItems);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //toast to cancel
                        Toast.makeText(getActivity(), "Emphasis cancelled", Toast.LENGTH_SHORT).show();
                        //update listener
                        listener.onCancel(EmphasisListDialogFragment.this);
                    }

                });
        return builder.create();
    }

    //set the selection ArrayList
    public void setSelectedItems(ArrayList<Integer> selectedItemsList) {
        selectedItems = selectedItemsList;
    }

    //for testing
    public String getSelectedItemsAsString() {
        String arrayToString;
        if (selectedItems != null) {
            arrayToString = selectedItems.toString();
            return arrayToString;
        } else {
            return "Target Array is Empty";
        }
    }

    @Override
    public void onListOk(ArrayList<Integer> selected) {
        //passes the ArrayList back to the activity
    }

    public void onResultOk(DialogFragment dialog){
        //not used here
    }

    @Override
    public void onCancel(DialogFragment dialog) {
        //dismiss the dialog
        dialog.dismiss();
    }

}
