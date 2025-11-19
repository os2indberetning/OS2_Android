/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.baseClasses;

import it_minds.dk.eindberetningmobil_android.R;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.core.graphics.Insets;

import it_minds.dk.eindberetningmobil_android.baseClasses.inner.ViewUtilClass;

/**
 *  BaseClass for shared Activity methods
 */
public abstract class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= 35) {
            // Edge-to-edge ON
            WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public <T extends View> T getViewById(@IdRes int id) throws ClassCastException {
        return ViewUtilClass.getViewById(this, id);
    }

    /**
     * Shows a back button in the actionbar
     */
    public void setActionbarBackDisplay() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Shows a back button in the actionbar with the supplied title
     * @param titleId
     */
    public void setActionbarBackDisplay(String titleId) {
        if (getSupportActionBar() != null) {
            setActionbarBackDisplay();
            getSupportActionBar().setTitle(titleId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //convenience function.
    public void hideSoftkeyboard() {
        ViewUtilClass.hideSoftkeyboard(this.getWindow());
    }


}
