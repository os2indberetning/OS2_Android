package it_minds.dk.eindberetningmobil_android.baseClasses;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import it_minds.dk.eindberetningmobil_android.baseClasses.inner.ViewUtilClass;

/**
 * Created by kasper on 28-06-2015.
 */
public class SimpleActivity extends AppCompatActivity {
    public <T extends View> T getViewById(@IdRes int id) throws ClassCastException {
        return ViewUtilClass.getViewById(this, id);
    }

    public void setActionbarBackDisplay() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void setActionbarBackDisplay(@StringRes int titleId) {
        if (getSupportActionBar() != null) {
            setActionbarBackDisplay();
            getSupportActionBar().setTitle(titleId);
        }
    }

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

    public void setListOfStringsToSpinner(List<String> data, Spinner spinner, ArrayAdapter<String> adapter) {
        adapter.clear();
        adapter.addAll(data);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    public void setListOfStringsToSpinner(List<String> data,@IdRes int spinnerId, ArrayAdapter<String> adapter) {
        Spinner spinner = getViewById(spinnerId);
        setListOfStringsToSpinner(data, spinner, adapter);
    }

    public int getSelectedIndexFromSpinner(@IdRes int viewId) {
        Spinner spinner = getViewById(viewId);
        if (spinner != null) {
            return spinner.getSelectedItemPosition();
        }
        return -1;
    }


    public void hideSoftkeyboard() {
        ViewUtilClass.hideSoftkeyboard(this.getWindow());
    }

    public void showSoftKeyboard(EditText editText) {
        if (editText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void setSpinnerIndex(int indexByValue,@IdRes int viewId) {
        Spinner spin = getViewById(viewId);
        spin.setSelection(indexByValue);
    }

}
