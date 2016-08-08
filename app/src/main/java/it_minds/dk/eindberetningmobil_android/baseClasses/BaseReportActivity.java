/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.baseClasses;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.OnData;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.models.Employments;
import it_minds.dk.eindberetningmobil_android.models.Purpose;
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.input.PurposeActivity;
import it_minds.dk.eindberetningmobil_android.views.input.TextInputView;
import it_minds.dk.eindberetningmobil_android.views.input.EmploymentActivity;
import it_minds.dk.eindberetningmobil_android.views.input.RateActivity;

/**
 * the base class for displaying and editing a report. This is used for both the pre phase, and the after phase.
 */
public class BaseReportActivity extends ProvidedSimpleActivity {

    protected DrivingReport report = new DrivingReport();
    private Employments employment;
    private final static int TEXT_INPUT_CODE = 556;

    private OnData<String> afterEditCallback;

    /**
     * helper function to show the regular text edit for a field
     *
     * @param callback
     * @param title
     * @param currentValue
     */
    public void showEdit(OnData<String> callback, String title, String currentValue) {
        showEdit(callback, title, currentValue, TextInputView.class);
    }

    /**
     * helper function to show the regular text edit for a field
     *
     * @param callback
     * @param title
     * @param currentValue
     * @param viewClass
     */
    public void showEdit(OnData<String> callback, String title, String currentValue, Class<?> viewClass) {
        afterEditCallback = callback;
        Intent intent = new Intent(this, viewClass);
        intent.putExtra(IntentIndexes.DATA_INDEX, currentValue);
        intent.putExtra(IntentIndexes.TITLE_INDEX, title);
        startActivityForResult(intent, TEXT_INPUT_CODE);
    }

    /**
     * base handling of the edit.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TEXT_INPUT_CODE && data != null) {
            if (afterEditCallback != null) {
                afterEditCallback.onData(data.getStringExtra(IntentIndexes.DATA_INDEX));
            } else {
                Log.e("temp", "bug");
            }
        }
    }

    //<editor-fold desc="Helpers function for various fields of driving report.">
    public void handlePurpose(@IdRes int purposeContainer, @IdRes final int purposeDesc) {
        findViewById(purposeContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setPurpose(data);
                        setPurposeText(data, purposeDesc);
                    }
                }, getString(R.string.purpose_title_edit), report.getPurpose(), PurposeActivity.class);
            }
        });
        if (report.getPurpose() != null && report.getPurpose().length() > 0) {
            setTextToView(purposeDesc, report.getPurpose());
        }
    }

    public void setPurposeText(String id, @IdRes int label) {
        TextView purposeText = (TextView) findViewById(label);
        Purpose purposeById = findPurposeByDescription(id);
        if (purposeById != null) {
            purposeText.setText(purposeById.getDescription());
        }
    }

    public Purpose findPurposeByDescription(String description) {
        ArrayList<Purpose> purposes = MainSettings.getInstance(this).getPurpose();
        if(purposes == null) return null;

        for (Purpose purpose : purposes) {
            if (description.equals(purpose.getDescription())) {
                return purpose;
            }
        }
        return null;
    }

    public void handleOrgLocation(@IdRes int container, @IdRes final int label) {
        findViewById(container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setOrgLocation(data);
                        setOrgText(data, label);
                    }
                }, getString(R.string.org_location_title_edit), report.getOrgLocation(), EmploymentActivity.class);
            }
        });
        if (report.getOrgLocation() != null && report.getOrgLocation().length() > 0) {
            setOrgText(report.getOrgLocation(), label);
        }
    }

    public void handleOrgLocationAfterTrip(@IdRes int container, @IdRes final int label, @IdRes final int fourKmRuleView) {
        findViewById(container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setOrgLocation(data);
                        setOrgText(data, label);
                        setFourKmRuleHidden(data, fourKmRuleView);
                    }
                }, getString(R.string.org_location_title_edit), report.getOrgLocation(), EmploymentActivity.class);
            }
        });
        if (report.getOrgLocation() != null && report.getOrgLocation().length() > 0) {
            setOrgText(report.getOrgLocation(), label);
            setFourKmRuleHidden(report.getOrgLocation(), fourKmRuleView);
        }
    }

    public void setFourKmRuleHidden(String orgId, int fourKmRuleViewResId) {
        View kmView = getViewById(fourKmRuleViewResId);
        employment = findEmployementById(orgId);
        if (employment != null) {
            if (employment.getFourKmRuleAllowed()) {
                kmView.setVisibility(View.VISIBLE);
            }
            else {
                kmView.setVisibility(View.GONE);
            }
        }
        else
        {
            kmView.setVisibility(View.GONE);
        }
    }

    public void setOrgText(String orgId, int resId) {
        TextView tv = getViewById(resId);
        employment = findEmployementById(orgId);
        if (employment != null) {
            tv.setText(employment.getEmploymentPosition());
        }
    }

    public void handleRate(@IdRes int container, @IdRes final int label) {
        findViewById(container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setRate(data);
                        setRateText(data, label);
                    }
                }, getString(R.string.rate_title_edit), report.getRate(), RateActivity.class);
            }
        });
        if (report.getRate() != null && report.getRate().length() > 0) {
            setRateText(report.getRate(), label);
        }
    }

    public void handleExtraDesc(@IdRes int container, @IdRes final int label) {
        findViewById(container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setExtraDescription(data);
                        setTextToView(label, data);
                    }
                }, getString(R.string.extra_description_title_edit), report.getExtraDescription());
            }
        });
        if (report.getExtraDescription() != null && report.getExtraDescription().length() > 0) {
            setTextToView(label, report.getExtraDescription());
        }
    }

    public Employments  findEmployementById(String id) {
        if (MainSettings.getInstance(this).getProfile() == null || MainSettings.getInstance(this).getProfile().getEmployments() == null) {
            return null;
        }
        ArrayList<Employments> employmentses = MainSettings.getInstance(this).getProfile().getEmployments();
        for (Employments emp : employmentses) {
            if (id.equals(emp.getId() + "")) {
                return emp;
            }
        }
        return null;
    }

    public void setRateText(String id, @IdRes int label) {
        TextView rateText = (TextView) findViewById(label);
        Rates rateById = findRateById(id);
        if (rateById != null) {
            rateText.setText(rateById.getDescription());
        }
    }

    public Rates findRateById(String id) {
        ArrayList<Rates> rates = MainSettings.getInstance(this).getRates();
        for (Rates rate : rates) {
            if (id.equals(rate.getId() + "")) {
                return rate;
            }
        }
        return null;
    }

    /**
     * validates that the required fields are set by the user.
     *
     * @return
     */
    public boolean validateCommonFields() {
        return (report.getPurpose() != null && report.getPurpose().length() > 0
                && report.getRate() != null && report.getRate().length() > 0
                && report.getOrgLocation() != null && report.getOrgLocation().length() > 0);
    }

    //</editor-fold>
}
