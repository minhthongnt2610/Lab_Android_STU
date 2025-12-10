package vn.edu.stu.quanlysinhvienpost.model;

public class Sinhvien {
    private int masv;
    private String tensv;

    public Sinhvien(int masv, String tensv) {
        this.masv = masv;
        this.tensv = tensv;
    }

    public int getMasv() { return masv; }
    public String getTensv() { return tensv; }

    @Override
    public String toString() {
        return masv + " - " + tensv;
    }
}
