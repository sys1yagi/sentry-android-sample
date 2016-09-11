package com.sys1yagi.sentry_android_sample;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.getsentry.raven.DefaultRavenFactory;
import com.getsentry.raven.RavenFactory;
import io.fabric.sdk.android.Fabric;

public class SentryApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        setupRaven();

        // You can use Crashlytics with.
        // You should call Fabric.with after initialize Raven.
        // Fabric.with(this, new Crashlytics());
    }

    public void setupRaven(){
        RavenFactory.registerFactory(new DefaultRavenFactory());
        Thread.setDefaultUncaughtExceptionHandler(
                new RavenUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler())
        );
    }
}
