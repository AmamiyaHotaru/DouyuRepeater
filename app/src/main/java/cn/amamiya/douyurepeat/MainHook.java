package cn.amamiya.douyurepeat;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.app.Application;

import cn.amamiya.douyurepeat.hooks.Hooks;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedBridge.log("[DouyuRepeat]Loaded app: " + lpparam.packageName);
        if (!lpparam.packageName.equals("air.tv.douyu.android")) {
            return;
        }


        String sourceDir = lpparam.appInfo.sourceDir;
        XposedBridge.log("当前App主dex路径: " + sourceDir);
        XposedHelpers.findAndHookMethod(android.app.Instrumentation.class, "callApplicationOnCreate", Application.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Hooks.init(lpparam.classLoader, sourceDir);
            }
        });


    }
}
