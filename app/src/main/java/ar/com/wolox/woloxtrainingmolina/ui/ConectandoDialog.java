package ar.com.wolox.woloxtrainingmolina.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import ar.com.wolox.woloxtrainingmolina.R;

public class ConectandoDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getActivity().getString(R.string.progressdialog_connecting)); // set your messages if not inflated from XML

        dialog.setCancelable(false);

        return dialog;
    }
}