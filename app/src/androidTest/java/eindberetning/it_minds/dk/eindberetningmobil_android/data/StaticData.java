/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package eindberetning.it_minds.dk.eindberetningmobil_android.data;

import org.joda.time.DateTime;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.models.Employments;
import it_minds.dk.eindberetningmobil_android.models.GPSCoordinateModel;
import it_minds.dk.eindberetningmobil_android.models.Profile;
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.models.internal.Authorization;

public class StaticData {
    public static Profile createSimpleProfile() {
        ArrayList<Employments> employess = new ArrayList<Employments>();
        employess.add(new Employments(999, "tester"));
        Authorization auth = createMockAuth();
        return (new Profile(0, "firstname", "lastname", "0.0", "0.0", employess, auth));
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
        DrivingReport result = new DrivingReport("purpose", "999", "1", "extra here.", false, true, false, false, new DateTime(), new DateTime(), 6000, 10.0d);
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

    public static Authorization createMockAuth() {
        return new Authorization("abc123def456");
    }
}
