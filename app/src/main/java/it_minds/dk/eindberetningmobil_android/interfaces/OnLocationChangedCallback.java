/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.interfaces;

import android.location.Location;

/**
 * describes a callback event for listening for location updates.
 * (Observer pattern)
 */
public interface OnLocationChangedCallback {
    void onNewLocation(Location location);
}
