package ar.com.wolox.woloxtrainingmolina;

import android.app.Application;

import ar.com.wolox.woloxtrainingmolina.api.ParseAPIHelper;
import retrofit.RestAdapter;

public class TrainingApp extends Application {

    private static ParseAPIHelper sParseAPIHelper;
    private static RestAdapter sRestAdapter;

    //The ParseAPIHelper instance is created on-demand if it hasn't been instantiated before
    public static ParseAPIHelper getParseApiHelper() {
        if (sParseAPIHelper == null) {
            setParseApiHelper(new ParseAPIHelper());
            sParseAPIHelper.setContentTypeToJson(true);
        }
        return sParseAPIHelper;
    }

    public static void setParseApiHelper(ParseAPIHelper sParseApiHelper) {
        TrainingApp.sParseAPIHelper = sParseApiHelper;
    }

    //The RestAdapter instance is created on-demand if it hasn't been instantiated before
    public static RestAdapter getRestAdapter() {
        if (sRestAdapter == null) {
            setRestAdapter(getParseApiHelper().getRestAdapter());
        }
        return sRestAdapter;
    }

    public static void setRestAdapter(RestAdapter sRestAdapter) {
        TrainingApp.sRestAdapter = sRestAdapter;
    }

}
