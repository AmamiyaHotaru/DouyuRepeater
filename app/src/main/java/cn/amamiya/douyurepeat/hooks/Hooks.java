package cn.amamiya.douyurepeat.hooks;

import de.robv.android.xposed.XposedBridge;

public class Hooks {
    static final IHook[] hooks = {

            new ForceAddOnePortraitHook(),
            new ForceAddOneLandscapeHook(),
            new AddSuffixSendDanmakuHook(),
            new AddSuffixAddOneHook(),
            new DanmakuEditTextPortraitHook(),
            new DanmakuEditTextLandscapeHook(),
            new ModifyHintHook()
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
