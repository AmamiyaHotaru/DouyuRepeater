package cn.amamiya.douyurepeat.hooks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import cn.amamiya.douyurepeat.utils.ContextUtils;
import cn.amamiya.douyurepeat.utils.SuffixUtils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class SettingHook implements IHook{
    @Override
    public String getHookName() {
        return "设置界面";
    }

    @Override
    public void hook(ClassLoader classLoader) throws Throwable {
        XposedBridge.log("[DouyuRepeat] 开始hook 设置界面");
        XposedHelpers.findAndHookMethod(
                "com.douyu.module.settings.activity.SetupActivity",
                classLoader,
                "onCreate",
                Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Activity activity = (Activity) param.thisObject;

                        ViewGroup rootContent = activity.findViewById(android.R.id.content);
                        if (rootContent == null) return;

                        // 找到 ScrollView -> LinearLayout
                        ViewGroup scrollView = (ViewGroup) ((ViewGroup) rootContent.getChildAt(0)).getChildAt(0);
                        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
                        if (linearLayout == null) return;

                        // 创建外层容器 LinearLayout
                        LinearLayout container = new LinearLayout(activity);
                        container.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        params.topMargin = dpToPx(container.getContext(), 10);
                        container.setLayoutParams(params);
                        container.setBackgroundColor(Color.WHITE);

                        // 创建 TextView
                        TextView newItem = new TextView(activity);
                        newItem.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                dpToPx(activity, 55)
                        ));
                        newItem.setGravity(Gravity.CENTER_VERTICAL);
                        newItem.setPadding(dpToPx(activity, 12), 0, 0, 0);
                        newItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                        newItem.setTextColor(Color.BLACK);
                        newItem.setText("弹幕后缀设置");

                        ContextUtils.init(newItem.getContext());
                        newItem.setOnClickListener(v -> {

                            SuffixUtils.showSuffixInputDialog(newItem.getContext());
                        });

                        // 把 TextView 加入到容器里
                        container.addView(newItem);

                        // 把容器加入到原来的布局里，位置0
                        linearLayout.addView(container, 0);
                    }

                }
        );
    }
    private int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

}
