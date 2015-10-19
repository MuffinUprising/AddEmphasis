package com.example.casey.addemphasis;

/**
 * Created by casey on 10/19/15.
 */

import android.support.v4.app.DialogFragment;

import java.io.InputStream;
import java.util.ArrayList;
public interface DialogListener {

    void onListOk(ArrayList<Integer> selected);
    void onResultOk(DialogFragment dialog);
    void onCancel(android.app.DialogFragment dialog);
}
