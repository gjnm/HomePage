
package page.home.com.gjnm.homepage.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;

public class GlobalConfigMgr {
    private static final String PREFS_FILE = "global_config";
    private static GlobalConfigMgr sInstance;

    private static final String TOP_HEIGHT = "top_height";


    private Context mContext;
    private SharedPreferences mPref;


    private GlobalConfigMgr(Context context) {
        mContext = context;
        mPref = mContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    }

    public static GlobalConfigMgr getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new GlobalConfigMgr(context.getApplicationContext());
        }
        return sInstance;
    }

    public void setTopHeight(int height) {
        mPref.edit().putInt(TOP_HEIGHT, height).apply();
    }

    public int getTopHeight() {
        return mPref.getInt(TOP_HEIGHT, 0);
    }

}