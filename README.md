# sentry-android-sample

The code is sample of the [Sentry](https://sentry.io/) for android.

## Setup sample

### Create sentry.properties

`app/sentry.properties`

```
sentryDns=https://YOUR_DNS
``` 

## Mechanism

The project uses [raven-java](https://github.com/getsentry/raven-java). raven-java is the official Java client for Sentry.


### Add custom Application class.

Add custom Application class and append setting to AndroidManifest.xml

```xml
<application
  android:name=".SentryApplication"
...
```

### Initialize RavenFactory on Application class.

On Android environment, You should set RavenFactory manually. [see more](https://github.com/getsentry/raven-java#android)

```java
public class SentryApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        setupRaven();
    }

    public void setupRaven(){
        RavenFactory.registerFactory(new DefaultRavenFactory());
    }
}
```
### Create RavenUncaughtExceptionHandler

```java
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
```

Set Thread.setDefaultUncaughtExceptionHandler.

```
public void setupRaven(){
    RavenFactory.registerFactory(new DefaultRavenFactory());
    Thread.setDefaultUncaughtExceptionHandler(
        new RavenUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler())
    );
}
```

## Use with Crashlytics

You can use Crashlytics with. You should call Fabric.with after initialize Raven.

```java
public class SentryApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        setupRaven();

        // You can use Crashlytics with.
        // You should call Fabric.with after initialize Raven.
        Fabric.with(this, new Crashlytics());
    }
//...
```

## License

```
MIT License

Copyright (c) 2016 Toshihiro Yagi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

