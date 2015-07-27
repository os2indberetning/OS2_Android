package it_minds.dk.eindberetningmobil_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.models.Employments;
import it_minds.dk.eindberetningmobil_android.models.Rates;

/**
 * Created by kasper on 27-07-2015.
 */
public class EmploymentAdapter extends ArrayAdapter<Employments> {
    public EmploymentAdapter(Context context, ArrayList<Employments> employments) {
        super(context, 0);
        addAll(employments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View toUse = convertView;
        if (convertView == null) {
            toUse = LayoutInflater.from(getContext()).inflate(R.layout.employmeet_list_item, parent, false);
        }
        TextView desc = (TextView) toUse.findViewById(R.id.employment_list_item_text);
        Employments item = getItem(position);
        desc.setText(item.getEmploymentPosition());
        return toUse;
    }
}
