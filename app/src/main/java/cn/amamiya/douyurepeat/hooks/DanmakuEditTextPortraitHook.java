

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
 * 竖屏hook输入框
 */
public class DanmakuEditTextPortraitHook implements IHook {


    @Override
    public String getHookName() {
        return "弹出弹幕后缀输入框（竖屏）";
    }

    @Override
    public void hook(ClassLoader classLoader) {
        XposedBridge.log("[DouyuRepeat] 开始hook" + getHookName());

        XposedHelpers.findAndHookMethod(
                "com.douyu.sdk.inputframe.v2.RamboInputFrameWidgetPortrait",
                classLoader,
                "initView",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        try {
                            View rootView = (View) param.thisObject;

                            int editTextId = 2131431273;
                            final EditText editText = rootView.findViewById(editTextId);


                            if (editText == null) {
                                XposedBridge.log("[DouyuRepeat]  找不到 DanmakuEditTextPortrait！");
                                return;
                            }

                            final Context context = editText.getContext();

                            ContextUtils.init(context.getApplicationContext());

                            editText.postDelayed(() -> {
                                editText.setHint("长按输入框修改弹幕后缀");
                                XposedBridge.log("[DouyuRepeat] ✅ 设置 hint 成功");
                            }, 2000);


                            XposedBridge.log("[DouyuRepeat]  获取到 DanmakuEditTextPortrait");


                            editText.setOnLongClickListener(view -> {
                                //XposedBridge.log("[DouyuRepeat] 长按弹出弹幕后缀输入对话框");
                                SuffixUtils.showSuffixInputDialog(context);
                                return true;
                            });

                        } catch (Throwable e) {
                            XposedBridge.log("[DouyuRepeat] ❌ Hook异常: " + e.getMessage());
                        }
                    }
                }
        );
    }
}










