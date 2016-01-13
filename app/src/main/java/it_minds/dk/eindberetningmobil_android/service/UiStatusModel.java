/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * a model of the current state about the
 */
public class UiStatusModel implements Parcelable {
    private String lastUpdated;
    private String accuracy;
    private String currentDistance;

    public UiStatusModel(String lastUpdated, String accuracy, String currentDistance) {
        this.lastUpdated = lastUpdated;
        this.accuracy = accuracy;
        this.currentDistance = currentDistance;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public String getCurrentDistance() {
        return currentDistance;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lastUpdated);
        dest.writeString(this.accuracy);
        dest.writeString(this.currentDistance);
    }

    protected UiStatusModel(Parcel in) {
        this.lastUpdated = in.readString();
        this.accuracy = in.readString();
        this.currentDistance = in.readString();
    }

    public static final Parcelable.Creator<UiStatusModel> CREATOR = new Parcelable.Creator<UiStatusModel>() {
        public UiStatusModel createFromParcel(Parcel source) {
            return new UiStatusModel(source);
        }

        public UiStatusModel[] newArray(int size) {
            return new UiStatusModel[size];
        }
    };
}
