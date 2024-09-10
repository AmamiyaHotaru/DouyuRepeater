package cn.amamiya.douyurepeat.hooks;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.view.View;

import java.lang.reflect.Field;
import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class DanmakuHook implements IHook{
    @Override
    public String getHookName() {
        return "滚动弹幕";
    }

    @Override
    public void hook(ClassLoader classLoader) throws Throwable {

        XposedBridge.log("[DouyuRepeat] 开始hook" + getHookName());
        final Class<?> rankBeanClass = XposedHelpers.findClass("com.douyu.lib.xdanmuku.bean.RankBean", classLoader);
        final Class<?> userInfoBeanClass = XposedHelpers.findClass("com.douyu.live.common.beans.UserInfoBean", classLoader);
        findAndHookMethod("com.douyu.module.player.p.danmuoption.papi.LandDanmuOptionView", classLoader, "h", rankBeanClass,userInfoBeanClass , boolean.class,HashMap.class, boolean.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                // 获取 LandDanmuOptionView 实例对象
                Object landDanmuOptionView = param.thisObject;

                // 获取 f45453h 对应的 View
                Field f45453hField = landDanmuOptionView.getClass().getDeclaredField("h");
                f45453hField.setAccessible(true);
                View f45453hView = (View) f45453hField.get(landDanmuOptionView);
                f45453hView.setVisibility(View.VISIBLE);
            }
        });
    }
}
