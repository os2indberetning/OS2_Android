package it_minds.dk.eindberetningmobil_android.server;

import android.content.Context;

import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.Token;
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

    public void verifyToken(ResultCallback<Boolean> callback) {

    }


    public void saveToken(Token token) {
        settings.setToken(token);
    }

    public Token getToken() {
        return settings.getToken();
    }
}
