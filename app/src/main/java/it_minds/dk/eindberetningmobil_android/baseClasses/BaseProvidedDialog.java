/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.baseClasses;

import android.content.Context;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.interfaces.SimpleDialog;

/**
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
