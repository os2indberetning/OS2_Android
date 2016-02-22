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

public class ErrorDialog extends BaseProvidedDialog {

    private final Context context;
    private final String message;

    //Used if standard dialog needs custom values
    private final View.OnClickListener customListener;
    private boolean isCancelable = true;
    private Dialog dialog;

    public ErrorDialog(Context context, String message) {
        super(context);
        this.context = context;
        this.message = message;
        this.customListener = null;
    }

    public ErrorDialog(Context context, String message, View.OnClickListener customListener) {
        super(context);
        this.context = context;
        this.message = message;
        this.customListener = customListener;
    }

    @Override
    public void showDialog() {
        dialog = new Dialog(context);
        dialog.setCancelable(isCancelable);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.error_dialog_view);
        TextView errorMsg = (TextView) dialog.findViewById(R.id.error_dialog_view_error_message);
        errorMsg.setText(message);
        TextView errorTitle = (TextView) dialog.findViewById(R.id.error_dialog_view_error_title);
        errorTitle.setText(R.string.error_dialog_title);
        if(customListener == null){
            dialog.findViewById(R.id.error_dialog_view_ok_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }else{
            dialog.findViewById(R.id.error_dialog_view_ok_btn).setOnClickListener(customListener);
        }

        dialog.show();
    }

    public void setIsCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    public void dismissDialog(){
        if(dialog != null){
            dialog.dismiss();
        }
    }
}
