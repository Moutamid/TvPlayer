package com.moutamid.tvplayer;

import android.app.Application;

import com.fxn.stash.Stash;

public class MyApplication extends Application {
    private static final String ONESIGNAL_APP_ID = "427724b6-c608-4cd3-832a-eadb7fe28b07";
    @Override
    public void onCreate() {
        super.onCreate();
        Stash.init(this);

        /*OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications();*/

    }
}
