package vn.edu.stu.karaokelist.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "BaiHat_TBL")
public class BaiHat {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "MABH")
    private String maBH;

    @ColumnInfo(name = "TENBH")
    private String tenBH;

    @ColumnInfo(name = "LOIBH")
    private String loiBH;

    @ColumnInfo(name = "TACGIA")
    private String tacGia;

    @ColumnInfo(name = "THELOAI")
    private String theLoai;

    // ⭐ SỬA LỖI TẠI ĐÂY
    @ColumnInfo(name = "YEUTHICH")
    @Nullable
    private Integer yeuThich;   // dùng Integer thay vì boolean

    public BaiHat() {}

    @NonNull
    public String getMaBH() {
        return maBH;
    }

    public void setMaBH(@NonNull String maBH) {
        this.maBH = maBH;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }

    public String getLoiBH() {
        return loiBH;
    }

    public void setLoiBH(String loiBH) {
        this.loiBH = loiBH;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    @Nullable
    public Integer getYeuThich() {
        return yeuThich;
    }

    public void setYeuThich(@Nullable Integer yeuThich) {
        this.yeuThich = yeuThich;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }
}
