/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.interfaces;

/**
 * describes an operations result, either it went good or bad (server for example).
 */
public interface ResultCallback<T> {
    void onSuccess(T result);
    void onError(Exception error);
}
