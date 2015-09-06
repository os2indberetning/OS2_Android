package it_minds.dk.eindberetningmobil_android.server;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.DriveReport;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.SaveableDriveReport;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;

/**
 * Created by kasper on 06-09-2015.
 */
public interface ServerInterface {
    String getBaseUrl();

    void setBaseUrl(String baseUrl);

    ImageLoader getImageLoader();

    void pairPhone(String pairCode, final ResultCallback<UserInfo> callback);

    void sendReport(SaveableDriveReport report, ResultCallback<UserInfo> callback);

    void sendReport(DriveReport report, ResultCallback<UserInfo> callback);

    void validateToken(Tokens currentToken, ResultCallback<UserInfo> callback);

    void getProviders(final ResultCallback<List<Provider>> callback);
}
