package se.futurememories.fixmycity;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Mattias on 14/05/16.
 */
public class FixMyCityApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/CircularStd-Book.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
