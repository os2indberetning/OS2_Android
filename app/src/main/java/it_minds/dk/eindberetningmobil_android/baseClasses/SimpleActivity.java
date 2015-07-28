package it_minds.dk.eindberetningmobil_android.baseClasses;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import it_minds.dk.eindberetningmobil_android.baseClasses.inner.ViewUtilClass;

/**
 * Created by kasper on 28-06-2015.
 */
public abstract class SimpleActivity extends AppCompatActivity {
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
     *
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

    //convinece function.
    public void hideSoftkeyboard() {
        ViewUtilClass.hideSoftkeyboard(this.getWindow());
    }


}
