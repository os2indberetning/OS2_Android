/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.views.dialogs;

import android.content.Context;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;

public class ConfirmationEndDrivingDialog extends ConfirmationDialog {


    public ConfirmationEndDrivingDialog(Context context, ResultCallback<Boolean> callback) {
        super(context,
                lookup(R.string.confirmation_end_driving_dialog_title, context),
                lookup(R.string.confirmation_end_driving_dialog_message, context),
                lookup(R.string.Ok, context), lookup(R.string.No, context),
                lookup(R.string.confirmation_end_driving_dialog_end_home, context), callback);
    }

    private static String lookup(int res, Context context) {
        return context.getString(res);
    }

}
