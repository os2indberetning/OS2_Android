package it_minds.dk.eindberetningmobil_android.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.interfaces.SimpleDialog;

/**
 * Created by kasper on 29-06-2015.
 */
public class ConfirmationEndDrivingDialog implements SimpleDialog {

    private Context context;
    private final String message;

    public ConfirmationEndDrivingDialog(Context context, String message) {
        this.context = context;

        this.message = message;
    }

    private String lookText(@StringRes int resId) {
        return context.getResources().getString(resId);
    }

    @Override
    public void showDialog() {
        final Dialog dialog = new Dialog(context);
        //dialog.setTitle(lookText(R.string.error_dialog_title));
        dialog.findViewById(R.id.confirmation_end_driving_dialog_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.confirmation_end_driving_dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) dialog.findViewById(R.id.confirmation_end_driving_dialog_end_home_checkbox);
                boolean endedAtHome =  cb.isChecked();
                //TODO do something here as well.
            }
        });
    }
}
