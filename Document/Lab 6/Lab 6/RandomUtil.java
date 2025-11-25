package vn.edu.stu.util;

public class RandomUtil {
    // Hàm phát sinh ngẫu nhiên 1 chuỗi với độ dài n
    public static String getAlphaNumericString(int n) {
        // Các ký tự được phép dùng để phát sinh chuỗi
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            // Phát sinh ngẫu nhiên một con số
            // từ 0 đến độ dài của alphaNumericString
            int index = (int) (alphaNumericString.length() * Math.random());

            // Thêm kí tự tại vị trí index vào cuối sb
            sb.append(alphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}

