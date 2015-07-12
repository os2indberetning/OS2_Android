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
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.TextInputView;
import it_minds.dk.eindberetningmobil_android.views.input.RateActivity;

/**
 * Created by kasper on 12-07-2015.
 */
public class BaseReportActivity extends ProvidedSimpleActivity {

    protected DrivingReport report = new DrivingReport();
    private final static int TEXT_INPUT_CODE = 556;

    private OnData<String> afterEditCallback;


    public void showEdit(OnData<String> callback, String title, String currentValue) {
        showEdit(callback, title, currentValue, TextInputView.class);
    }

    public void showEdit(OnData<String> callback, String title, String currentValue, Class<?> viewClass) {
        afterEditCallback = callback;
        Intent intent = new Intent(this, viewClass);
        intent.putExtra(IntentIndexes.DATA_INDEX, currentValue);
        intent.putExtra(IntentIndexes.TITLE_INDEX, title);
        startActivityForResult(intent, TEXT_INPUT_CODE);
    }

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


    public void handlePurpose(@IdRes int purposeContainer, @IdRes final int purposeDesc) {
        findViewById(purposeContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setPurpose(data);
                        setTextToView(purposeDesc, data);
                    }
                }, getString(R.string.purpose_title_edit), report.getPurpose());
            }
        });
        if (report.getPurpose() != null && report.getPurpose().length() > 0) {
            setTextToView(purposeDesc, report.getPurpose());
        }
    }

    public void handleOrgLocation(@IdRes int container, @IdRes final int label) {
        findViewById(container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setOrgLocation(data);
                        setTextToView(label, data);
                    }
                }, getString(R.string.org_location_title_edit), report.getOrgLocation());
            }
        });
        if (report.getOrgLocation() != null && report.getOrgLocation().length() > 0) {
            setTextToView(label, report.getOrgLocation());
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

    public void setRateText(String id,@IdRes int label) {
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
}
