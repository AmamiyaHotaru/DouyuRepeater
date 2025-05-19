package cn.amamiya.douyurepeat.hooks;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import cn.amamiya.douyurepeat.utils.ContextUtils;
import cn.amamiya.douyurepeat.utils.SuffixUtils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;


/**
 * 横屏hook输入框
 */
public class DanmakuEditTextLandscapeHook implements IHook {
    @Override
    public String getHookName() {
        return "弹出弹幕后缀输入框（横屏）";
    }

    @Override
    public void hook(ClassLoader classLoader) throws Throwable {

        XposedBridge.log("[DouyuRepeat] 开始hook" + getHookName());
        XposedHelpers.findAndHookMethod(
                "com.douyu.sdk.inputframe.v2.RamboInputFrameWidget",
                classLoader,
                "initView",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            View rootView = (View) param.thisObject;
                            int editTextId = 2131431273; // 如果横屏ID不同，换成对应的
                            final EditText editText = rootView.findViewById(editTextId);

                            if (editText == null) {
                                XposedBridge.log("[DouyuRepeat] 横屏找不到 DanmuEditText！");
                                return;
                            }


                            Context context = editText.getContext();
                            ContextUtils.init(context.getApplicationContext());
                            editText.postDelayed(() -> {
                                editText.setHint("长按输入框修改弹幕后缀");
                                XposedBridge.log("[DouyuRepeat] 横屏设置 hint 成功");
                            }, 2000);

                            editText.setOnLongClickListener(view -> {
                                //XposedBridge.log("[DouyuRepeat] 长按弹出弹幕后缀输入对话框");
                                SuffixUtils.showSuffixInputDialog(context);
                                return true;
                            });

                        } catch (Throwable e) {
                            XposedBridge.log("[DouyuRepeat] 横屏 Hook异常: " + e.getMessage());
                        }
                    }
                }
        );

    }
}
