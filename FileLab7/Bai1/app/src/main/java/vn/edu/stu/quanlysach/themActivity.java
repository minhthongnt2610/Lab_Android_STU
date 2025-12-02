package vn.edu.stu.quanlysach;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.edu.stu.quanlysach.model.Sach;
import vn.edu.stu.quanlysach.util.AppDatabase;

public class themActivity extends AppCompatActivity {

    EditText edtTen, edtTacGia, edtNamXuatBan;
    Button btnLuu, btnHuy;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them);

        db = AppDatabase.getAppDatabase(this);

        addControls();
        addEvents();
    }

    private void addControls() {
        edtTen = findViewById(R.id.edtTen);
        edtTacGia = findViewById(R.id.edtTacGia);
        edtNamXuatBan = findViewById(R.id.edtNamXuatBan);

        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtTen.getText().toString().trim();
                String tacgia = edtTacGia.getText().toString().trim();
                String namStr = edtNamXuatBan.getText().toString().trim();

                if (ten.isEmpty()) {
                    Toast.makeText(themActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer namXB = null;
                if (!namStr.isEmpty()) {
                    namXB = Integer.parseInt(namStr);
                }

                Sach sach = new Sach();
                sach.setTen(ten);
                sach.setTacgia(tacgia);
                sach.setNamXuatBan(namXB);

                db.sachDao().insert(sach);

                Toast.makeText(themActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();

                finish(); // quay về MainActivity
            }
        });

        btnHuy.setOnClickListener(view -> finish());
    }
}
