package it_minds.dk.eindberetningmobil_android.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.interfaces.SimpleDialog;

/**
 * Created by kasper on 29-06-2015.
 */
public class ErrorDialog implements SimpleDialog {

    private Context context;
    private final String message;

    public ErrorDialog(Context context, String message) {
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
    }
}
