package it_minds.dk.eindberetningmobil_android.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseProvidedDialog;

/**
 * Created by kasper on 29-06-2015.
 */
public class ErrorDialog extends BaseProvidedDialog {

    private final Context context;
    private final String message;

    public ErrorDialog(Context context, String message) {
        super(context);
        this.context = context;
        this.message = message;
    }


    @Override
    public void showDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.error_dialog_view);
        TextView errorMsg = (TextView) dialog.findViewById(R.id.error_dialog_view_error_message);
        errorMsg.setText(message);
        TextView errorTitle = (TextView) dialog.findViewById(R.id.error_dialog_view_error_title);
        errorTitle.setText(R.string.error_dialog_title);
        dialog.findViewById(R.id.error_dialog_view_ok_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
