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
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseProvidedDialog;


public class InaccuracyDialog extends BaseProvidedDialog {

    private final Context context;

    public InaccuracyDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.error_dialog_view); // Reusing view as the layout is the same
        TextView errorMsg = (TextView) dialog.findViewById(R.id.error_dialog_view_error_message);
        errorMsg.setText(R.string.inaccuracy_dialog_message);
        TextView title = (TextView) dialog.findViewById(R.id.error_dialog_view_error_title);
        title.setText(R.string.inaccuracy_dialog_title);
        dialog.findViewById(R.id.error_dialog_view_ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
