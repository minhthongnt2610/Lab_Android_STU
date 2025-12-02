package vn.edu.stu.karaokelist.util;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBConfigUtil {
    final static String DATABASE_NAME = "KaraokeList.sqlite";
    final static String DB_PATH_SUFFIX = "/databases/";

    public static void copyDatabaseFromAssets(Context context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            // Tạo thư mục chứa CSDL nếu chưa có
            File dbDir = new File(context.getApplicationInfo().dataDir
                    + DB_PATH_SUFFIX);
            if (!dbDir.exists())
                dbDir.mkdir();

            InputStream is = null;
            OutputStream os = null;
            try {
                // Tạo file mới
                is = context.getAssets().open(DATABASE_NAME);
                String outputFilePath = context.getApplicationInfo().dataDir
                        + DB_PATH_SUFFIX + DATABASE_NAME;
                os = new FileOutputStream(outputFilePath);

                // Chép nội dung từ file CSDL trong assets vào thư mục CSDL
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                Toast.makeText(
                        context,
                        "Đã chép CSDL xong",
                        Toast.LENGTH_LONG
                ).show();
            } catch (Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            } finally {
                try {
                    os.close();
                } catch (IOException e) {
                }
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

