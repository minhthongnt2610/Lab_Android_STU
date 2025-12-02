package vn.edu.stu.quanlysach.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Sach_TBL")
public class Sach {
    @PrimaryKey(autoGenerate = true)
    private int ma;
    @NonNull
    private String ten;
    private String tacgia;
    @ColumnInfo(name = "namxuatban")
    @Nullable
    private Integer namXuatBan;

    public Sach() {
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public Integer getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(Integer namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    @Override
    public String toString() {
        return "Mã: " + ma + "\n" + "Tên: " + ten + "\n" + "Tác giả: " + tacgia + "\n" + "Năm xuất bản: " + namXuatBan + "\n";
    }
}
