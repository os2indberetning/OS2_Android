package it_minds.dk.eindberetningmobil_android.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseProvidedDialog;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;

/**
 * Created by kasper on 29-06-2015.
 */
public class ConfirmationEndDrivingDialog extends BaseProvidedDialog {

    private final Context context;
    private final String message;
    private ResultCallback<Boolean> callback;

    public ConfirmationEndDrivingDialog(Context context, String message) {
        super(context);
        this.context = context;
        this.message = message;
    }

    private String lookText(@StringRes int resId) {
        return context.getResources().getString(resId);
    }

    public void setCallback(final ResultCallback<Boolean> callback) {
        this.callback = callback;
    }

    @Override
    public void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirmation_dialog_view);
        TextView titleView = (TextView) dialog.findViewById(R.id.confirmation_end_driving_dialog_title);
        setTitleColorForText(titleView);

        TextView okBtn = (TextView) dialog.findViewById(R.id.confirmation_end_driving_dialog_ok);
        setColorForText(okBtn);
        TextView noBtn = (TextView) dialog.findViewById(R.id.confirmation_end_driving_dialog_no);
        setReverseColorsForText(noBtn);
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
                CheckBox cb = (CheckBox) dialog.findViewById(R.id.confirmation_end_driving_dialog_end_home_checkbox);
                boolean endedAtHome = cb.isChecked();
                callback.onSuccess(endedAtHome);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
