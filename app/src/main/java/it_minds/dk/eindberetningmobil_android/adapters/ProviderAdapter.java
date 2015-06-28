package it_minds.dk.eindberetningmobil_android.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.models.Provider;

/**
 * Created by kasper on 28-06-2015.
 */
public class ProviderAdapter extends ArrayAdapter<Provider> {


    public ProviderAdapter(Context context, List<Provider> data) {
        super(context, android.R.layout.simple_list_item_1);
        this.addAll(data);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            v = View.inflate(getContext(), android.R.layout.simple_list_item_1, null);
        }


        return v;
    }
}
