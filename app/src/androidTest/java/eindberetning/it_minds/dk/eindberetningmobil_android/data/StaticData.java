package eindberetning.it_minds.dk.eindberetningmobil_android.data;

import org.joda.time.DateTime;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.models.Employments;
import it_minds.dk.eindberetningmobil_android.models.GPSCoordinateModel;
import it_minds.dk.eindberetningmobil_android.models.Profile;
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;

/**
 * Created by kasper on 06-09-2015.
 */
public class StaticData {
    public static Profile createSimpleProfile() {
        ArrayList<Employments> employess = new ArrayList<Employments>();
        employess.add(new Employments(999, "tester"));
        ArrayList<Tokens> tokenses = new ArrayList<>();
        tokenses.add(createSimpleToken());
        return (new Profile(0, "firstname", "lastname", "0.0", "0.0", employess, tokenses));
    }

    public static UserInfo createSimpleUserInfo() {
        return new UserInfo(createSimpleProfile(), createSimpleRates());
    }

    private static ArrayList<Rates> createSimpleRates() {
        ArrayList<Rates> rates = new ArrayList<>();
        rates.add(new Rates(1, "car", "8000"));
        rates.add(new Rates(2, "airplane ", "8001"));
        return rates;

    }

    public static DrivingReport createSimpleDrivingReport() {
        DrivingReport result = new DrivingReport("purpose", "999", "1", "extra here.", false, true, false, new DateTime(), new DateTime(), 6000);
        result.getgpsPoints().add(createSimpleGpsPoint1());
        result.getgpsPoints().add(createSimpleGpsPoint2());
        return result;
    }

    private static GPSCoordinateModel createSimpleGpsPoint1() {
        return new GPSCoordinateModel(2.0, 5.0, false);
    }

    private static GPSCoordinateModel createSimpleGpsPoint2() {
        return new GPSCoordinateModel(3.0, 6.0, false);
    }

    public static Tokens createSimpleToken() {
        return new Tokens("", "123456", 1);
    }
}
