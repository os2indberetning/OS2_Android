package it_minds.dk.eindberetningmobil_android.views.dialogs;

import android.content.Context;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;

/**
 * Created by kasper on 29-06-2015.
 */
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
