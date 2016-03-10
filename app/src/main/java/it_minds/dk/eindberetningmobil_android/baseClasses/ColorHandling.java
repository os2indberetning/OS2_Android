/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.baseClasses;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

public class ColorHandling {

    private final Context context;

    public ColorHandling(Context context) {
        this.context = context;
    }

    public void setReverseColorsForText(TextView tv) {
        Provider provider = MainSettings.getInstance(context).getProvider();
        if (provider != null) {
            setColorTextView(tv, provider.getTextColor(), provider.getSecondaryColor());
        }
    }

    public void setColorForText(TextView tv) {
        Provider provider = MainSettings.getInstance(context).getProvider();
        if (provider != null) {
            setColorTextView(tv, provider.getSecondaryColor(), provider.getTextColor());
        }

    }

    public void setTitleColorForText(TextView tv) {
        Provider provider = MainSettings.getInstance(context).getProvider();
        if (provider != null) {
            setColorTextView(tv, provider.getPrimaryColor(), provider.getTextColor());
        }
    }

    private void setColorTextView(TextView tv, String backgroundColor, String foregroundColor) {
        try {
            tv.setBackgroundColor(Color.parseColor(backgroundColor));
            tv.setTextColor((Color.parseColor(foregroundColor)));
        } catch (Exception e) {

        }
    }

    public void setColorButton(Button button) {
        Provider provider = MainSettings.getInstance(context).getProvider();
        if(provider != null){
            button.setBackgroundColor(Color.parseColor(provider.getSecondaryColor()));
            button.setTextColor(Color.parseColor(provider.getTextColor()));
        }
    }
}
