/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * a wrapper for jsonobject, that  doesnt throw exceptions, but returns null values instead.
 */
public class SafeJsonHelper extends JSONObject {

    @Override
    public JSONObject put(String name, boolean value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            Logger.getLogger("SafeJsonObject").log(Level.WARNING, "", e);
            return null;
        }
    }

    @Override
    public JSONObject put(String name, double value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            Logger.getLogger("SafeJsonObject").log(Level.WARNING, "", e);
            return null;
        }
    }

    @Override
    public JSONObject put(String name, int value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            Logger.getLogger("SafeJsonObject").log(Level.WARNING, "", e);
            return null;
        }
    }

    @Override
    public JSONObject put(String name, long value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            Logger.getLogger("SafeJsonObject").log(Level.WARNING, "", e);
            return null;
        }
    }

    @Override
    public JSONObject put(String name, Object value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            Logger.getLogger("SafeJsonObject").log(Level.WARNING, "", e);
            return null;
        }
    }
}
