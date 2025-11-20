package vn.edu.stu.vd1.model;


import java.io.Serializable;
import java.util.Date;

import vn.edu.stu.vd1.util.FormatUtil;

public class Congviec implements Serializable {
    private String ten;
    private Date han;

    public Congviec(Date han, String ten) {
        this.han = han;
        this.ten = ten;
    }

    public Congviec() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Date getHan() {
        return han;
    }

    public void setHan(Date han) {
        this.han = han;
    }

    @Override
    public String toString() {
        return ten + " - " + FormatUtil.formatDateTime(this.han);
    }
}
