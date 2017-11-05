package page.home.com.gjnm.homepage.app;

import android.app.Application;

/**
 * Created by gaojian12 on 17/10/8.
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        sInstance = this;
        super.onCreate();
    }

}
