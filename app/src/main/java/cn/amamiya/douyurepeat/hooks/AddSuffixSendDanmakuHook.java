package cn.amamiya.douyurepeat.hooks;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import cn.amamiya.douyurepeat.utils.ContextUtils;
import cn.amamiya.douyurepeat.utils.SuffixUtils;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * 发送弹幕添加后缀
 */
public class AddSuffixSendDanmakuHook implements IHook {


    @Override
    public String getHookName() {
        return "发送弹幕添加后缀";
    }

    @Override
    public void hook(ClassLoader classLoader) {
        XposedBridge.log("[DouyuRepeat] 开始hook" + getHookName());
        try {
            Class<?> clazz = classLoader.loadClass("com.douyu.danmusend.ValidCheckDanmuSenderNeuron");

            XposedHelpers.findAndHookMethod(clazz, "Sy", HashMap.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

                    Context context = ContextUtils.getContext();

                    // 下面你原先处理参数逻辑
                    @SuppressWarnings("unchecked")
                    HashMap<String, String> map = (HashMap<String, String>) param.args[0];
                    String content = map.get("content");
                    if (content != null) {
                        content += SuffixUtils.getSavedSuffix(context);
                        map.put("content", content);
                    }
                    XposedBridge.log("[DouyuRepeat] 修改后 Sy参数: " + map);

                }
            });

        } catch (Throwable e) {
            XposedBridge.log("[DouyuRepeat] Hook失败: " + e.getMessage());
        }
    }

}
