package it_minds.dk.eindberetningmobil_android.baseClasses.inner;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Spinner;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tvede on 28-01-2015.
 */
public class ViewUtilClass {

    //<editor-fold desc="keyboard features">
    public static void hideSoftkeyboard(Window win) {
        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static void hideSoftkeyboard(Activity act) {
        hideSoftkeyboard(act.getWindow());
    }
    //</editor-fold>


    //<editor-fold desc="Textview features">
    public static String getTextFromSpinner(final View vg, @IdRes int spinnerId) {
        Spinner spinner = getViewById(vg, spinnerId);
        if (spinner != null) {
            return spinner.getSelectedItem().toString();
        }
        return null;
    }
    //</editor-fold>


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
