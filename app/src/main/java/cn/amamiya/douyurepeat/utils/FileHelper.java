package cn.amamiya.douyurepeat.utils;

import de.robv.android.xposed.XposedBridge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {

    public static String readFileToString(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            XposedBridge.log("[DouyuRepeat]读取文件内容时出错：" + e.getMessage());
        }
        return stringBuilder.toString();
    }

    public static void modifyFileContent(File file, String newContent) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(newContent);
            XposedBridge.log("[DouyuRepeat]文件内容修改成功");
        } catch (IOException e) {
            XposedBridge.log("[DouyuRepeat]修改文件内容时出错：" + e.getMessage());
        }
    }
}
