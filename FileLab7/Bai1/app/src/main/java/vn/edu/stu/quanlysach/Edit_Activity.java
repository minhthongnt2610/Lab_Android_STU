package vn.edu.stu.quanlysach;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.edu.stu.quanlysach.model.Sach;
import vn.edu.stu.quanlysach.util.AppDatabase;

public class Edit_Activity extends AppCompatActivity {

    EditText edtTen, edtTacGia, edtNamXB;
    Button btnLuu, btnHuy;
    AppDatabase db;
    int maSach;        // Mã sách được truyền từ MainActivity
    Sach sach;         // Đối tượng sách cần sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        db = AppDatabase.getAppDatabase(this);

        addControls();
        nhanMaSach();
        loadDuLieu();
        addEvents();
    }

    private void addControls() {
        edtTen = findViewById(R.id.edtTen);
        edtTacGia = findViewById(R.id.edtTacGia);
        edtNamXB = findViewById(R.id.edtNamXB);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
    }

    private void nhanMaSach() {
        maSach = getIntent().getIntExtra("MASACH", -1);
    }

    private void loadDuLieu() {
        sach = db.sachDao().findById(maSach);
        if (sach != null) {
            edtTen.setText(sach.getTen());
            edtTacGia.setText(sach.getTacgia());
            edtNamXB.setText(sach.getNamXuatBan() == null ? "" : sach.getNamXuatBan().toString());
        }
    }

    private void addEvents() {
        btnLuu.setOnClickListener(view -> {
            new androidx.appcompat.app.AlertDialog.Builder(Edit_Activity.this)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có muốn lưu các thay đổi không?")
                    .setPositiveButton("Lưu", (dialog, which) -> {
                        sach.setTen(edtTen.getText().toString());
                        sach.setTacgia(edtTacGia.getText().toString());
                        String nam = edtNamXB.getText().toString().trim();
                        sach.setNamXuatBan(nam.isEmpty() ? null : Integer.parseInt(nam));
                        db.sachDao().update(sach);
                        Toast.makeText(Edit_Activity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });

        btnHuy.setOnClickListener(view -> finish());
    }
}
