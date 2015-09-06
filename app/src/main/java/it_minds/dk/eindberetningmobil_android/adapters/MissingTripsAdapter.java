package it_minds.dk.eindberetningmobil_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.DistanceDisplayer;
import it_minds.dk.eindberetningmobil_android.models.internal.SaveableReport;

/**
 * Created by kasper on 01-09-2015.
 */
public class MissingTripsAdapter extends ArrayAdapter<SaveableReport> {

    private LayoutInflater inflator;

    public MissingTripsAdapter(Context context) {
        super(context, 0);
        inflator = inflator.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewToUse = convertView;
        if (convertView == null) {
            viewToUse = inflator.inflate(R.layout.missing_trip_item_view, parent, false);
        }
        SaveableReport report = getItem(position);
        TextView purporse = (TextView) viewToUse.findViewById(R.id.missing_trip_item_purposeText);
        purporse.setText(getContext().getString(R.string.purpose_prefix)+" : "+report.getPurpose());

        TextView distanceText = (TextView) viewToUse.findViewById(R.id.missing_trip_item_distanceText);

        TextView reportLabel = (TextView) viewToUse.findViewById(R.id.missing_trip_item_reportLabel);

        distanceText.setText(getContext().getString(R.string.distance_prefix)+" : "+DistanceDisplayer.formatDistance(report.getTotalDistance())+" km");
        //get rate here
        TextView rateText = (TextView) viewToUse.findViewById(R.id.missing_trip_item_rateText);
        rateText.setText(getContext().getString(R.string.rate_prefix)+" : "+"");
        if(report.getCreatedAt()!=null) {
            reportLabel.setText(getContext().getString(R.string.repport_prefix) + " " + report.getCreatedAt().toString("dd/MM - yyyy"));
        }else{
            reportLabel.setText("");
        }

        return viewToUse;
    }
}
