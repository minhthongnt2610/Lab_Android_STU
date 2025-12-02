package vn.edu.stu.karaokelist.util;


import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import vn.edu.stu.karaokelist.dao.BaiHatDao;
import vn.edu.stu.karaokelist.model.BaiHat;

@Database(entities = {BaiHat.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract BaiHatDao baiHatDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DBConfigUtil.DATABASE_NAME)
                    .createFromAsset(DBConfigUtil.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
