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

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.models.Employments;

/**
 * handles displaying a list of employments.
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
        Employments item = getItem(position);

        TextView desc = (TextView) toUse.findViewById(R.id.employment_list_item_text);
        desc.setText(item.getEmploymentPosition());

        TextView subDesc = (TextView) toUse.findViewById(R.id.employment_list_item_subtext);
        String manNr = item.getManNr();
        if (manNr.isEmpty() || manNr == null || manNr.equals("null")) {
            subDesc.setVisibility(View.GONE);
        } else {
            subDesc.setText(manNr);
        }

        return toUse;
    }
}
