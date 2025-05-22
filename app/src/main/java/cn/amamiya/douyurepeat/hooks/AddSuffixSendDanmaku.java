package cn.amamiya.douyurepeat.hooks;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cn.amamiya.douyurepeat.utils.ContextUtils;
import cn.amamiya.douyurepeat.utils.SuffixUtils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 发送弹幕添加后缀
 */
public class AddSuffixSendDanmaku implements IHook {

    private final Set<String> hookedMethods = new HashSet<>();

    @Override
    public String getHookName() {
        return "发送弹幕添加后缀";
    }

    @Override
    public void hook(ClassLoader classLoader) throws ClassNotFoundException {
        XposedBridge.log("[DouyuRepeat] 开始hook " + getHookName());
        Class<?> clazz = classLoader.loadClass("com.douyu.lib.xdanmuku.x.JniDanmu");
        Method[] methods = clazz.getMethods();

        // hook 参数只有一个并且是hashmap 返回值是int的发送弹幕函数
        for (Method method : methods) {
            Class<?>[] params = method.getParameterTypes();
            if (params.length != 1) {
                continue;
            }
            if (params[0] != HashMap.class) {
                continue;
            }
            if (method.getReturnType() != int.class) {
                continue;
            }

            String methodName = method.getName();
            XposedBridge.log("[DouyuRepeat] methodName: " + methodName);
            // 防止重复hook
            if (hookedMethods.contains(methodName)) {
                continue;
            }
            hookedMethods.add(methodName);

            XposedHelpers.findAndHookMethod(clazz, methodName, HashMap.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    @SuppressWarnings("unchecked")
                    HashMap<String, String> map = (HashMap<String, String>) param.args[0];
                    if (map != null) {
                        String content = map.get("content");
                        if (content != null) {
                            content = content + SuffixUtils.getSavedSuffix(ContextUtils.getContext());
                            map.put("content", content);
                        }

                    } else {
                        XposedBridge.log("[DouyuRepeat] senddanmumap is null");
                    }

                }
            });
        }
    }

}
