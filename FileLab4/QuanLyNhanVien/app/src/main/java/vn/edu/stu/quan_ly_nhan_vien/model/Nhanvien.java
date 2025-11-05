package vn.edu.stu.quan_ly_nhan_vien.model;

public class Nhanvien {
    private String ma;
    private String ten;
    private String sdt;
    public Nhanvien(String ma, String ten, String sdt) {
        this.ma = ma;
        this.ten = ten;
        this.sdt = sdt;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return
                "Mã: " + ma + '\n' +
                        "Tên: " + ten + '\n' +
                        "SDT: " + sdt + '\n'
                ;
    }
}
