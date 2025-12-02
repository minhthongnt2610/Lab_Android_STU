package vn.edu.stu.quanlysach.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vn.edu.stu.quanlysach.model.Sach;

@Dao
public interface SachDao {
    @Query("SELECT * FROM Sach_TBL")
    List<Sach> getAll();
    @Delete
    int delete(Sach sach);
    @Insert
    void insert(Sach sach);

    @Query("SELECT * FROM Sach_TBL WHERE ma = :id")
    Sach findById(int id);

    @Update
    void update(Sach sach);

}
