package net.cleonet.cleo.photofeed_galileo;

import android.app.Application;

import net.cleonet.cleo.photofeed_galileo.domain.di.DomainModule;

/**
 * Created by Pepe on 9/8/17.
 */

public class PhotoFeedApp extends Application {

    private final static String EMAIL_KEY = "email";
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";
    private final static String FIREBASE_URL = "https://cleoPhotoFeed.firebaseio.com/";

    private PhotoFeedAppModule photoFeedAppModule;
    private DomainModule domainModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModules();
    }

    private void initModules() {
        photoFeedAppModule = new PhotoFeedAppModule(this);
        domainModule = new DomainModule(FIREBASE_URL);
    }

    private void initFirebase() {
        //Firebase.setAndroidContext(this);
    }

    public String getEmailKey() {
        return EMAIL_KEY;
    }

    public String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }

    public String getFirebaseUrl() {
        return FIREBASE_URL;
    }
}
