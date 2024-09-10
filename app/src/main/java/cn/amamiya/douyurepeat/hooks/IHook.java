package cn.amamiya.douyurepeat.hooks;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public interface IHook {
    String getHookName();
    void hook(ClassLoader classLoader) throws Throwable;
}
