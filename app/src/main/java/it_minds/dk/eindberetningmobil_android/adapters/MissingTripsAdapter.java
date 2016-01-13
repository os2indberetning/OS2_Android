/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.DistanceDisplayer;
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.models.internal.SaveableReport;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

public class MissingTripsAdapter extends ArrayAdapter<SaveableReport> {

    private LayoutInflater inflator;

    private HashMap<String, Rates> lookupMap = new HashMap<>();

    public MissingTripsAdapter(Context context) {
        super(context, 0);
        inflator = LayoutInflater.from(context);
        createLookupMap();
    }

    private void createLookupMap() {
        ArrayList<Rates> rates = MainSettings.getInstance(getContext()).getRates();
        for (Rates r : rates) {
            lookupMap.put(r.getId() + "", r);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewToUse = convertView;
        if (convertView == null) {
            viewToUse = inflator.inflate(R.layout.missing_trip_item_view, parent, false);
        }
        SaveableReport report = getItem(position);
        TextView purporse = (TextView) viewToUse.findViewById(R.id.missing_trip_item_purposeText);
        purporse.setText(getContext().getString(R.string.purpose_prefix) + " : " + report.getPurpose());

        TextView distanceText = (TextView) viewToUse.findViewById(R.id.missing_trip_item_distanceText);

        TextView reportLabel = (TextView) viewToUse.findViewById(R.id.missing_trip_item_reportLabel);

        distanceText.setText(getContext().getString(R.string.distance_prefix) + " : " + DistanceDisplayer.formatDistance(report.getTotalDistance()) + " km");

        TextView rateText = (TextView) viewToUse.findViewById(R.id.missing_trip_item_rateText);
        rateText.setText(getContext().getString(R.string.rate_prefix) + " : " + getRateText(report.getRateid()));
        if (report.getCreatedAt() != null) {
            reportLabel.setText(getContext().getString(R.string.repport_prefix) + " " + report.getCreatedAt().toString("dd/MM - yyyy"));
        } else {
            reportLabel.setText("");
        }

        return viewToUse;
    }

    private String getRateText(String rateId) {
        if (lookupMap.containsKey(rateId)) {
            return lookupMap.get(rateId).getDescription();
        } else {
            return "";
        }
    }
}
