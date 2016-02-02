/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.models.Purpose;
import it_minds.dk.eindberetningmobil_android.models.Rates;

/**
 * an adapter capable of displaying a purpose item.
 */
public class PurposeAdapter extends ArrayAdapter<Purpose> {

    public PurposeAdapter(Context context, ArrayList<Purpose> purposes) {
        super(context, 0);
        addAll(purposes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View toUse = convertView;
        if (convertView == null) {
            toUse = LayoutInflater.from(getContext()).inflate(R.layout.purpose_list_item, parent, false);
        }
        TextView description = (TextView) toUse.findViewById(R.id.purpose_list_item_desc);
        TextView uses = (TextView) toUse.findViewById(R.id.purpose_list_item_uses);
        Purpose item = getItem(position);
        description.setText(item.getDescription());

        //Set used text:
        String times;
        if (item.getUses() == 1) {
            times = getContext().getString(R.string.purpose_used_once);
        } else {
            times = getContext().getString(R.string.purpose_used_more_than_once);
        }

        String usesString = getContext().getString(R.string.purpose_used_x_y, item.getUses(), times);
        uses.setText(usesString);
        return toUse;
    }
}
