package cn.amamiya.douyurepeat.hooks;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hooks {
    static final IHook[] hooks = {

            new DialogHook(),
            new DanmakuHook()
    };

    public static void init(ClassLoader classLoader){
        for (IHook hook : hooks) {
            try {
                hook.hook(classLoader);
            } catch (Throwable e) {
                XposedBridge.log("[DouyuRepeat] " + hook.getHookName()+"hook失败");
                XposedBridge.log("[DouyuRepeat] " + e);
            }
        }
    }
}
