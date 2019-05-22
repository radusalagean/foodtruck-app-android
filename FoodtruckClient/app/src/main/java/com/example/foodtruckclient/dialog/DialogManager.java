package com.example.foodtruckclient.dialog;

import android.content.Context;
import android.content.DialogInterface;

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

    public void showAlertDialog(@StringRes int title, @StringRes int message,
                                @StringRes int positiveMessage, @StringRes int negativeMessage,
                                AlertDialog.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveMessage, positiveClickListener);
        builder.setNegativeButton(negativeMessage, (((dialog, which) -> dialog.dismiss())));
        builder.show();
    }

    public void showListAlertDialog(@StringRes int title, String[] items,
                                    DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, onClickListener);
        builder.show();
    }
}
