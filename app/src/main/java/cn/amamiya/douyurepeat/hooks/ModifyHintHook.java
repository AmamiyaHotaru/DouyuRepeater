package cn.amamiya.douyurepeat.hooks;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class ModifyHintHook implements IHook{
    @Override
    public String getHookName() {
        return "修改输入框提示词";
    }

    @Override
    public void hook(ClassLoader classLoader) {

        XposedBridge.log("[DouyuRepeat] 开始hook" + getHookName());
        XposedHelpers.findAndHookMethod("com.douyu.module.player.p.memedanmu.MemeDanmu", classLoader, "Si", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult("长按输入框修改弹幕后缀");
            }
        });
    }
}
