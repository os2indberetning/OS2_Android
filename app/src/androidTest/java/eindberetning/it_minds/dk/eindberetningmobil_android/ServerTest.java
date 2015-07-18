package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.test.ApplicationTestCase;

import org.junit.Test;

import it_minds.dk.eindberetningmobil_android.MainApplication;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.server.ServerHandler;

/**
 * Created by kasper on 18-07-2015.
 */
public class ServerTest extends ApplicationTestCase<MainApplication> {
    public ServerTest() {
        super(MainApplication.class);
    }

    @Test
    public void testValidate(){
        ServerHandler.getInstance(mContext).setBaseUrl("https://ework.favrskov.dk/FavrskovMobilityAPI/api/");
        ServerHandler.getInstance(mContext).validateToken(new Tokens("", "", 1), new ResultCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {

            }

            @Override
            public void onError(Exception error) {

            }
        });
        ServerHandler.getInstance(mContext).validateToken(null, new ResultCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                assertTrue("should call error case",false );
            }

            @Override
            public void onError(Exception error) {

            }
        });


    }
}
