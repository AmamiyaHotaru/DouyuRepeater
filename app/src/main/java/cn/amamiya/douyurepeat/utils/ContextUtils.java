package cn.amamiya.douyurepeat.utils;

import android.content.Context;

public class ContextUtils {

    private static Context appContext;

    private ContextUtils() {

    }

    public static void init(Context context) {
        if (context != null) {
            appContext = context.getApplicationContext();
        }
    }


    public static Context getContext() {
        if (appContext == null) {
            throw new IllegalStateException("ContextUtils is not initialized. Call init() first.");
        }
        return appContext;
    }
}
