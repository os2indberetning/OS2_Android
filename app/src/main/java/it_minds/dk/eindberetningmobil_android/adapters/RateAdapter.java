package it_minds.dk.eindberetningmobil_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.models.Rates;

/**
 * Created by kasper on 12-07-2015.
 */
public class RateAdapter extends ArrayAdapter<Rates> {

    public RateAdapter(Context context, ArrayList<Rates> rates) {
        super(context, 0);
        addAll(rates);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View toUse = convertView;
        if (convertView == null) {
            toUse = LayoutInflater.from(getContext()).inflate(R.layout.rate_list_item, parent, false);
        }
        TextView description = (TextView) toUse.findViewById(R.id.rate_list_item_desc);
        TextView year = (TextView) toUse.findViewById(R.id.rate_list_item_year);
        Rates item = getItem(position);
        description.setText(item.getDescription());
        year.setText(item.getYear());
        return toUse;
    }
}
