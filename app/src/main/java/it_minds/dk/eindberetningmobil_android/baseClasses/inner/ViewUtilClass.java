/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.baseClasses.inner;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewUtilClass {

    //<editor-fold desc="keyboard features">
    public static void hideSoftkeyboard(Window win) {
        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    //<editor-fold desc="util features">
    public static <T extends View> T getViewById(final View vg, @IdRes int id) throws ClassCastException {
        View v = vg.findViewById(id);
        if (v == null) {
            return null;
        } else {
            try {
                return (T) v;
            } catch (ClassCastException cls) {
                Logger.getLogger("simpleActivity").log(Level.SEVERE, "INVALID getViewById", cls);
                return null;
            }
        }
    }

    public static <T extends View> T getViewById(final Activity act, @IdRes int id) throws ClassCastException {
        View v = act.findViewById(android.R.id.content);
        return getViewById(v, id);
    }


    //</editor-fold>


}
