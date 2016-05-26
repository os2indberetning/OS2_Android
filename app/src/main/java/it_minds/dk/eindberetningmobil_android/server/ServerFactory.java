/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.server;

import android.content.Context;

public class ServerFactory {
    private static ServerInterface serverIface;

    /**
     * Mostly used by testing, to override the default server.
     *
     * @param newServerIface
     */
    public static void setServerIface(ServerInterface newServerIface) {
        serverIface = newServerIface;
    }

    public static ServerInterface getInstance(Context context) {
        if (serverIface == null) {
            serverIface = ServerHandler.getInstance(context);
        }
        return serverIface;
    }

    public static void resetToDefault(Context context){
        serverIface = ServerHandler.getInstance(context);
    }

}
