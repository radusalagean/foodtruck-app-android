package com.example.foodtruckclient.dialog;

import android.content.Context;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.example.foodtruckclient.R;

public class DialogManager {

    private Context context;

    public DialogManager(Context context) {
        this.context = context;
    }

    public void showBasicAlertDialog(@StringRes int title, @StringRes int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton(R.string.alert_dialog_action_dismiss,
                ((dialog, which) -> dialog.dismiss()));
        builder.show();
    }
}
