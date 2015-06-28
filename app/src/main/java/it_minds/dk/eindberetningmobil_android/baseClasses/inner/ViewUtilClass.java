package it_minds.dk.eindberetningmobil_android.baseClasses.inner;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tvede on 28-01-2015.
 */
public class ViewUtilClass {

    //<editor-fold desc="keyboard features">
    public static void hideSoftkeyboard(Window win) {
        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//just to be sure.
    }

    public static void hideSoftkeyboard(Activity act) {
        hideSoftkeyboard(act.getWindow());
    }
    //</editor-fold>


    //<editor-fold desc="Intent features">
    public static void showIntentForMaps(Activity act, double lat, double lng) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:" + lat + "," + lng));
        act.startActivity(intent);
    }

    public static void showIntentForMaps(Activity act, String address) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:0,0?q=" + Uri.encode(address)));
        act.startActivity(intent);
    }


    public static void showInternetIntent(Activity act, String url) {
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = "http://" + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        act.startActivity(intent);
    }

    public static void showEmailIntent(Activity act, String email, String subject, String intentText) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        act.startActivity(Intent.createChooser(emailIntent, intentText));
    }
    //</editor-fold>


    //<editor-fold desc="Textview features">
    public static String getTextFromSpinner(final View vg,@IdRes int spinnerId) {
        Spinner spinner = getViewById(vg, spinnerId);
        return spinner.getSelectedItem().toString();
    }

    public static void SetTextToView(final Activity act, String text,@IdRes int resId) {
        View v = act.findViewById(resId);
        if (v instanceof TextView) {
            ((TextView) v).setText(text);
        }
    }

    public static void SetTextToView(final View v, final String text,@IdRes final int resId) {
        View vg = v.findViewById(resId);
        if (vg instanceof TextView) {
            ((TextView) vg).setText(text);
        }
    }

    public static void SetTextToView(final View vg,@StringRes int textResId, @IdRes int viewId) {
        View v = vg.findViewById(viewId);
        if (v instanceof TextView) {
            ((TextView) v).setText(textResId);
        }
    }

    public static void SetTextToView(final Activity act ,@StringRes int textResId, @IdRes int viewId) {
        View v = act.findViewById(viewId);
        if (v instanceof TextView) {
            ((TextView) v).setText(textResId);
        }
    }

    public static String getTextFromView(final Activity act,@IdRes int resId) {
        View v = act.findViewById(resId);
        if (v instanceof TextView) {
            return ((TextView) v).getText().toString();
        }
        return "";
    }

    public static String getTextFromView(final View vg,@IdRes int resId) {
        View v = vg.findViewById(resId);
        if (v instanceof TextView) {
            return ((TextView) v).getText().toString();
        }
        return "";
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
