package cn.amamiya.douyurepeat.hooks;

import de.robv.android.xposed.XposedBridge;

public class Hooks {
    static final IHook[] hooks = {

            new ForceAddOnePortraitHook(),
            new ForceAddOneLandscapeHook(),
            new AddSuffixSendDanmaku(),
            new DanmakuEditHookOld(),
            new SettingHook(),
            new DanmakuEditHookNew(),
            new DisableUpdateHook()
    };

    public static void init(ClassLoader classLoader, String sourceDir) {
        for (IHook hook : hooks) {
            try {
                hook.hook(classLoader);
            } catch (Throwable e) {
                XposedBridge.log("[DouyuRepeat] " + hook.getHookName() + "hook失败");
                XposedBridge.log("[DouyuRepeat] " + e);
            }
        }
    }
}
