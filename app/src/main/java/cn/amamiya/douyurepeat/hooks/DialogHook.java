package cn.amamiya.douyurepeat.hooks;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.view.View;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class DialogHook implements IHook{
    @Override
    public String getHookName() {
        return "非全屏界面弹窗";
    }

    @Override
    public void hook(ClassLoader classLoader) throws Throwable {

        XposedBridge.log("[DouyuRepeat] 开始hook" + getHookName());


        XposedHelpers.findAndHookMethod("tv.douyu.danmuopt.bean.OptFunBean", classLoader, "b", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                // 让b()方法永远返回false
                return false;
            }
        });



        findAndHookMethod("tv.douyu.danmuopt.view.PortraitDanmuOptionDialog", classLoader, "c", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // 调用原方法
                super.afterHookedMethod(param);
                // 获取目标对象
                Object targetObject = param.thisObject;

                
                Field field = targetObject.getClass().getField("g");
                field.setAccessible(true); // 设置字段可访问
                View f134844g = (View) field.get(targetObject); // 获取字段的值
                f134844g.setVisibility(View.VISIBLE);


            }
        });

    }
}
