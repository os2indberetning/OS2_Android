package it_minds.dk.eindberetningmobil_android.baseClasses;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 02-07-2015.
 */
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

}
