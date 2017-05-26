package com.example.raghavendra.vidnet.widgets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.raghavendra.vidnet.R;

/**
 * Created by raghavendra on 26/05/17.
 */

public class VidNetProgressDialog extends DialogFragment {
    private static final String KEY_MESSAGE = "message";

    private String message;

    public static VidNetProgressDialog newInstance(String message) {
        VidNetProgressDialog d = new VidNetProgressDialog();
        Bundle args = new Bundle();
        args.putString(KEY_MESSAGE, message);
        d.setArguments(args);
        return d;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        message = getArguments().getString(KEY_MESSAGE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_progress, null, false);
        ((TextView) v.findViewById(R.id.message)).setText(message);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();

    }
}
