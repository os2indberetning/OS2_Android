/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.baseClasses;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

public abstract class ProvidedSimpleActivity extends SimpleActivity {

    private ColorHandling colorHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colorHandler =new ColorHandling(this);
        setTopbarColor();
    }

    private void setTopbarColor() {
        Provider provider = MainSettings.getInstance(this).getProvider();
        if (getSupportActionBar() != null && provider != null && provider.getPrimaryColor() != null) {
            try {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(provider.getPrimaryColor())));
            } catch (Exception e) {

            }
        }
    }

    public void setColorForText(TextView tv) {
        colorHandler.setColorForText(tv);
    }

    public void setReverseColorsForText(TextView tv) {
        colorHandler.setReverseColorsForText(tv);
    }

    public void setTextToView(@IdRes int id, String val) {
        TextView tv = getViewById(id);
        if (tv != null) {
            tv.setText(val);
        }
    }

}
