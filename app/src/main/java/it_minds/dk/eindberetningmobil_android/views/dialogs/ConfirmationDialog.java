/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseProvidedDialog;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;

/**
 * Generic dialog which has a positive and a negative button and requires user decision.
 */
public class ConfirmationDialog extends BaseProvidedDialog {
    private final Context context;
    private final String title;
    private final String message;
    private final String yesText;
    private final String noText;
    private String optionalCheckboxText;
    private final ResultCallback<Boolean> callback;
    private boolean canCancel = false;


    public ConfirmationDialog(Context context, String title, String message, String yesText, String noText,
                              String optionalCheckboxText,
                              ResultCallback<Boolean> callback) {
        super(context);
        this.context = context;
        this.title = title;
        this.message = message;
        this.yesText = yesText;
        this.noText = noText;
        this.optionalCheckboxText = optionalCheckboxText;
        this.callback = callback;
    }

    @Override
    public void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirmation_dialog_view);
        dialog.setCancelable(canCancel);
        dialog.setCanceledOnTouchOutside(canCancel);
        TextView titleView = (TextView) dialog.findViewById(R.id.confirmation_end_driving_dialog_title);
        setTitleColorForText(titleView);
        titleView.setText(title);

        TextView okBtn = (TextView) dialog.findViewById(R.id.confirmation_end_driving_dialog_ok);
        okBtn.setText(yesText);
        setColorForText(okBtn);

        TextView noBtn = (TextView) dialog.findViewById(R.id.confirmation_end_driving_dialog_no);
        setReverseColorsForText(noBtn);
        noBtn.setText(noText);

        TextView mainContent = (TextView) dialog.findViewById(R.id.confirmation_dialog_view_message);
        mainContent.setText(message);


        final CheckBox cb = (CheckBox) dialog.findViewById(R.id.confirmation_end_driving_dialog_end_home_checkbox);

        if (optionalCheckboxText == null) {
            cb.setVisibility(View.GONE);
        } else {
            cb.setVisibility(View.VISIBLE);
        }


        //dialog.setTitle(lookText(R.string.error_dialog_title));
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.onError(null);
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionalCheckboxText != null) {
                    boolean checkboxStatus = cb.isChecked();
                    callback.onSuccess(checkboxStatus);
                } else {
                    callback.onSuccess(true);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setCanCancel(boolean b) {
        this.canCancel = b;
    }
}
