package cn.amamiya.douyurepeat.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import de.robv.android.xposed.XposedBridge;

public class SuffixUtils {


    private static final String PREF_NAME = "douyurepeat_prefs";
    private static final String PREF_KEY_SUFFIX = "danmu_tail";

    /**
     * 弹出修改弹幕后缀的对话框
     */
    public static void showSuffixInputDialog(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        final EditText input = new EditText(context);
        input.setHint("请输入弹幕后缀");

        String oldSuffix = prefs.getString(PREF_KEY_SUFFIX, "");
        input.setText(oldSuffix);
        input.setSelection(oldSuffix.length());

        new AlertDialog.Builder(context)
                .setTitle("修改弹幕后缀")
                .setView(input)
                .setPositiveButton("保存", (dialog, which) -> {
                    String suffix = input.getText().toString().trim();
                    prefs.edit().putString(PREF_KEY_SUFFIX, suffix).apply();
                    XposedBridge.log("[DouyuRepeat] ✅ 弹幕后缀保存成功: " + suffix);
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    /**
     * 获取已保存的弹幕后缀
     */
    public static String getSavedSuffix(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_KEY_SUFFIX, "");
    }
}
