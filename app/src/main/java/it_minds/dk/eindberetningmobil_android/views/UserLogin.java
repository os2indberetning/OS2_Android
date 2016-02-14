package it_minds.dk.eindberetningmobil_android.views;

import android.os.Bundle;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;

public class UserLogin extends ProvidedSimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }

    private void setupUI(){
        setContentView(R.layout.activity_user_login);
        setActionbarBackDisplay();

    }

}
