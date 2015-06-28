package it_minds.dk.eindberetningmobil_android.server;

import android.content.Context;

import it_minds.dk.eindberetningmobil_android.interfaces.ServerCallback;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 28-06-2015.
 */
public class LoginHandler {
    private MainSettings settings;

    private ServerHandler serverHandler;


    public LoginHandler(Context context) {
        serverHandler = ServerHandler.getInstance(context);
        settings = MainSettings.getInstance(context);
    }

    public boolean haveToken() {
        return settings.haveToken();
    }

    public void verifyToken(ServerCallback<Boolean> callback) {

    }


    public void saveToken(String token) {
        settings.setToken(token);
    }

    public String getToken() {
        return settings.getToken();
    }
}
