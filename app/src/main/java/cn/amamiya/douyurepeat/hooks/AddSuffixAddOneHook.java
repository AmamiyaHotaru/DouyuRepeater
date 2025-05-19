package cn.amamiya.douyurepeat.hooks;

import android.content.Context;

import cn.amamiya.douyurepeat.utils.ContextUtils;
import cn.amamiya.douyurepeat.utils.SuffixUtils;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * +1添加后缀
 */
public class AddSuffixAddOneHook implements IHook {


    @Override
    public String getHookName() {
        return "加一弹幕添加后缀";
    }

    @Override
    public void hook(ClassLoader classLoader) {
        XposedBridge.log("[DouyuRepeat] 开始hook" + getHookName());

        // Hook 1: 取消弹幕长度限制
        try {
            XposedHelpers.findAndHookMethod(
                    "tv.douyu.liveplayer.inputpanel.LPDanmuCDMgr",
                    classLoader,
                    "Ns",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            param.setResult(999);
                        }
                    }
            );
        } catch (Exception e) {
            XposedBridge.log("[DouyuRepeat] [Ns] Hook 异常: " + android.util.Log.getStackTraceString(e));
        }

        // Hook 2: 弹幕发送，修改内容
        try {
            Class<?> userInfoBeanClass = classLoader.loadClass("com.douyu.live.common.beans.UserInfoBean");

            XposedHelpers.findAndHookMethod(
                    "tv.douyu.danmuopt.manager.DanmuOptMgr",
                    classLoader,
                    "r7",
                    userInfoBeanClass,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Object userInfoBean = param.args[0];
                            if (userInfoBean != null) {

                                java.lang.reflect.Field contentField = userInfoBeanClass.getDeclaredField("content");
                                contentField.setAccessible(true);
                                String originalContent = (String) contentField.get(userInfoBean);
                                if (originalContent == null) originalContent = "";

                                Context context = ContextUtils.getContext();

                                String newContent = originalContent + SuffixUtils.getSavedSuffix(context);
                                contentField.set(userInfoBean, newContent);

                                XposedBridge.log("[DouyuRepeat] [r7] 弹幕内容修改为: " + newContent);
                            }
                        }
                    }
            );
        } catch (Exception e) {
            XposedBridge.log("[DouyuRepeat] [r7] Hook 异常: " + android.util.Log.getStackTraceString(e));
        }
    }
}
