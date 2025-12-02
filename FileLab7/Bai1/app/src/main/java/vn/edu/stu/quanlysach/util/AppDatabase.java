package vn.edu.stu.quanlysach.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import vn.edu.stu.quanlysach.dao.SachDao;
import vn.edu.stu.quanlysach.model.Sach;

@Database(entities = {Sach.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract SachDao sachDao();
    public static AppDatabase getAppDatabase(Context context) {
        if(instance == null){
            instance = androidx.room.Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DBConfigUtil.DATABASE_NAME
            ).createFromAsset( DBConfigUtil.DATABASE_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }
    public static void destroyInstance(){
        instance = null;
    }


}
