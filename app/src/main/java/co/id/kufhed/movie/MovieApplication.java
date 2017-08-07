package co.id.kufhed.movie;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by madinf on 8/7/17.
 */

public class MovieApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
