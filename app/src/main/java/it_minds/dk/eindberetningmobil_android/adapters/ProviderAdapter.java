/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import it_minds.dk.eindberetningmobil_android.models.Provider;

/**
 * handles displaying a list of providers.
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
        TextView tv = (TextView) v;
        Provider prov = getItem(position);

        tv.setText(prov.getName());


        return v;
    }
}
