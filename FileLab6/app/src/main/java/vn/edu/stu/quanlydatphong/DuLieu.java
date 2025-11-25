package vn.edu.stu.quanlydatphong;

import java.util.ArrayList;

import vn.edu.stu.quanlydatphong.model.DatPhong;

public class DuLieu {
    public static ArrayList<DatPhong> dsDatPhong;

    static {
        dsDatPhong = new ArrayList<>();
    }

    public static DatPhong layDatPhong(int index) {
        if (index >= 0 && index < dsDatPhong.size()) {
            return dsDatPhong.get(index);
        }
        return null;
    }

    public static void themDatPhong(DatPhong dp) {
        dsDatPhong.add(dp);
    }

    public static void xoaDatPhong(int index) {
        if (index >= 0 && index < dsDatPhong.size()) {
            dsDatPhong.remove(index);
        }
    }
}
