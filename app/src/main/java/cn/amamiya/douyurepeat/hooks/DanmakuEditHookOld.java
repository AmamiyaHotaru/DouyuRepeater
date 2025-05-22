package cn.amamiya.douyurepeat.hooks;

import android.widget.EditText;

import cn.amamiya.douyurepeat.utils.ContextUtils;
import cn.amamiya.douyurepeat.utils.SuffixUtils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;


/**
 * 横屏hook输入框
 */
public class DanmakuEditHookOld implements IHook {
    @Override
    public String getHookName() {
        return "弹出弹幕后缀输入框（旧版本）";
    }

    @Override
    public void hook(ClassLoader classLoader) throws Throwable {



        // 旧版本
        XposedBridge.log("[DouyuRepeat] 开始hook" + getHookName());
        XposedHelpers.findAndHookMethod(
                "com.douyu.sdk.inputframe.widget.BaseInputArea", // 类名
                classLoader,
                "getInputView", // 方法名
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Object result = param.getResult();
                        if (result instanceof EditText) {
                            EditText editText = (EditText) result;

                            ContextUtils.init(editText.getContext().getApplicationContext());
                            editText.setHint("长按输入框修改弹幕后缀");
                            editText.setOnLongClickListener(v -> {
                                SuffixUtils.showSuffixInputDialog(editText.getContext());
                                return true;
                            });


                            //XposedBridge.log("[DouyuRepeat] 成功 Hook 输入框，并添加长按事件");
                        } else {
                            XposedBridge.log("[DouyuRepeat] getInputView 返回的不是 EditText，实际类型: " + (result != null ? result.getClass().getName() : "null"));
                        }
                    }
                }
        );


        // 新版本
        XposedHelpers.findAndHookMethod("com.douyu.sdk.inputframe.v2.RamboInputFrameWidget", classLoader, "getInputView", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                if (result instanceof EditText) {
                    EditText editText = (EditText) result;

                    ContextUtils.init(editText.getContext().getApplicationContext());
                    editText.setHint("长按输入框修改弹幕后缀");
                    editText.setOnLongClickListener(v -> {
                        SuffixUtils.showSuffixInputDialog(editText.getContext());
                        return true;
                    });


                    //XposedBridge.log("[DouyuRepeat] 成功 Hook 输入框，并添加长按事件");
                } else {
                    XposedBridge.log("[DouyuRepeat] getInputView 返回的不是 EditText，实际类型: " + (result != null ? result.getClass().getName() : "null"));
                }
            }
        });


    }
}
