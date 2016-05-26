/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.baseClasses;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * BaseClass for shared Activity methods
 */
public abstract class ProvidedSimpleActivity extends SimpleActivity {

    private ColorHandling colorHandler;
    private ProgressDialog spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize spinner
        spinner = new ProgressDialog(this);

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

    public void showProgressDialog(){
        if(spinner !=null){
            spinner.setIndeterminate(true);
            spinner.setCancelable(false);
            spinner.setMessage(getString(R.string.please_wait));
            spinner.show();
        }
    }

    public void dismissProgressDialog(){
        if(spinner != null) {
            spinner.dismiss();
        }
    }

}
