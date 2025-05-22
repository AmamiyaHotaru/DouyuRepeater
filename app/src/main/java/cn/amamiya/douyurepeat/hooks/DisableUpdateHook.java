package cn.amamiya.douyurepeat.hooks;

import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class DisableUpdateHook implements IHook{
    @Override
    public String getHookName() {
        return "禁止更新";
    }

    @Override
    public void hook(ClassLoader classLoader) throws Throwable {

        XposedBridge.log("[DouyuRepeat] 开始hook" + getHookName());
        XposedHelpers.findAndHookMethod(
                "com.douyu.module.update.bean.UpdateBean",
                classLoader,
                "isUpdate",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(false); // 屏蔽更新
                    }
                }
        );

        XposedHelpers.findAndHookMethod(
                "com.douyu.module.update.bean.UpdateBean",
                classLoader,
                "isForceUpdate",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(false); // 屏蔽强制更新
                    }
                }
        );


    }
}
