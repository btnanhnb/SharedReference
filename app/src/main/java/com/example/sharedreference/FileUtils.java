package com.example.sharedreference;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtils {

    private static File getAppFolder(Context context) {
        File folder = new File(context.getFilesDir(), "myfolder");
        if (!folder.exists()) folder.mkdirs();
        return folder;
    }

    public static String getTextFromFile(String fileName, Context context) {
        try {
            File file = new File(getAppFolder(context), fileName);
            if (!file.exists()) return "";

            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            br.close();
            return sb.toString();

        } catch (Exception e) {
            Log.e("FileUtils", "Lỗi đọc file: " + e.getMessage());
            return "";
        }
    }

    public static void taoFileTXT(String noidung, String fileName, Context context) {
        try {
            File folder = getAppFolder(context);
            File file = new File(folder, fileName);

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(noidung);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            Log.e("FileUtils", "Lỗi tạo file: " + e.getMessage());
        }
    }
}
