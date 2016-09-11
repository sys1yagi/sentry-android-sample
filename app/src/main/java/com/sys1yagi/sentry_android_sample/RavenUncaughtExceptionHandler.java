package com.sys1yagi.sentry_android_sample;

import com.getsentry.raven.Raven;
import com.getsentry.raven.RavenFactory;
import com.getsentry.raven.dsn.Dsn;

class RavenUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static Raven INSTANCE = RavenFactory.ravenInstance(new Dsn(BuildConfig.sentryDns));

    private Thread.UncaughtExceptionHandler defaultHandler;

    RavenUncaughtExceptionHandler(Thread.UncaughtExceptionHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        INSTANCE.sendException(e);
        defaultHandler.uncaughtException(t, e);
    }
}
