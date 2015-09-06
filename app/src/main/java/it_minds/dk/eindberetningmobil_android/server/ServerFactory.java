package it_minds.dk.eindberetningmobil_android.server;

import android.content.Context;

/**
 * Created by kasper on 06-09-2015.
 */
public class ServerFactory {
    private static ServerInterface serverIface;

    /**
     * Mosly used by testing, to override the default server.
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
