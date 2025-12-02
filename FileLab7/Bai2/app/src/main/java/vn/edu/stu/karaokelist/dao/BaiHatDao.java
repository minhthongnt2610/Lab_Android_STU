package vn.edu.stu.karaokelist.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vn.edu.stu.karaokelist.model.BaiHat;
@Dao
public interface BaiHatDao {
    @Query("SELECT * FROM BaiHat_TBL")
    List<BaiHat> getAll();

    @Update
    int update(BaiHat baiHat);
    @Delete
    int delete(BaiHat baiHat);
    @Query("SELECT * FROM BaiHat_TBL WHERE MABH = :id")
    BaiHat findById(String id);
    @Query("SELECT * FROM BaiHat_TBL WHERE " +
            "MABH LIKE '%' || :keyword || '%' OR " +
            "TENBH LIKE '%' || :keyword || '%' OR " +
            "LOIBH LIKE '%' || :keyword || '%' OR " +
            "TACGIA LIKE '%' || :keyword || '%' OR " +
            "THELOAI LIKE '%' || :keyword || '%'")
    List<BaiHat> search(String keyword);


    @Query("SELECT * FROM BaiHat_TBL WHERE MABH LIKE :dieukien" +
            " OR TENBH LIKE :dieukien OR LOIBH LIKE :dieukien" +
            " OR TACGIA LIKE :dieukien OR THELOAI LIKE :dieukien")
    List<BaiHat> findBaiHat(String dieukien);
}
