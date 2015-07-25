package it_minds.dk.eindberetningmobil_android.baseClasses;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.interfaces.SimpleDialog;

/**
 * Created by kasper on 02-07-2015.
 * simple baseclass for a provider dialog (a colored dialog).
 */
public abstract class BaseProvidedDialog implements SimpleDialog {

    private ColorHandling colorHandler;

    public BaseProvidedDialog(Context context) {
        this.colorHandler = new ColorHandling(context);
    }

    public void setColorForText(TextView tv) {
        colorHandler.setColorForText(tv);
    }

    public void setReverseColorsForText(TextView tv) {
        colorHandler.setReverseColorsForText(tv);
    }

    public void setTitleColorForText(TextView tv) {
        colorHandler.setTitleColorForText(tv);
    }

}
