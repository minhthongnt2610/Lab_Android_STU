package vn.edu.stu.tuy_bien_giao_dien;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import vn.edu.stu.tuy_bien_giao_dien.adapter.NhanvienAdapter;
import vn.edu.stu.tuy_bien_giao_dien.model.Nhanvien;


public class MainActivity extends AppCompatActivity {
    EditText txtMa, txtTen, txtSdt;
    Button btnLuu;
    ArrayList<Nhanvien> dsNhanVien;
    ArrayAdapter<Nhanvien> adapter;
    ListView lvDsNhanVien;

    // Biến lưu vị trí nhân viên đang được chọn để sửa
    int viTriDangSua = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addControls() {
        txtMa = findViewById(R.id.txtMa);
        txtTen = findViewById(R.id.txtTen);
        txtSdt = findViewById(R.id.txtSdt);
        btnLuu = findViewById(R.id.btnLuu);
        lvDsNhanVien = findViewById(R.id.lvDsNhanVien);

        dsNhanVien = new ArrayList<>();
        adapter = new NhanvienAdapter(MainActivity.this, R.layout.item_nhanvien, dsNhanVien);
        lvDsNhanVien.setAdapter(adapter);
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = txtMa.getText().toString().trim();
                String ten = txtTen.getText().toString().trim();
                String sdt = txtSdt.getText().toString().trim();

                if (ma.isEmpty() || ten.isEmpty() || sdt.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Nếu đang sửa nhân viên
                if (viTriDangSua != -1) {
                    Nhanvien nv = dsNhanVien.get(viTriDangSua);
                    nv.setMa(ma);
                    nv.setTen(ten);
                    nv.setSdt(sdt);
                    viTriDangSua = -1;
                    Toast.makeText(MainActivity.this, "Cập nhật nhân viên thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm mới nhân viên
                    Nhanvien nv = new Nhanvien(ma, ten, sdt);
                    dsNhanVien.add(nv);
                    Toast.makeText(MainActivity.this, "Thêm nhân viên mới thành công", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
                clearFields();
            }
        });
        // Khi nhấn giữ để xóa nhân viên
        lvDsNhanVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Đã xóa nhân viên " + dsNhanVien.get(position).getTen(), Toast.LENGTH_SHORT).show();
                dsNhanVien.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        // Khi nhấn 1 lần để sửa nhân viên
        lvDsNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Nhanvien nv = dsNhanVien.get(position);
                txtMa.setText(nv.getMa());
                txtTen.setText(nv.getTen());
                txtSdt.setText(nv.getSdt());
                viTriDangSua = position;
                Toast.makeText(MainActivity.this, "Đang chỉnh sửa: " + nv.getTen(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        txtMa.setText("");
        txtTen.setText("");
        txtSdt.setText("");
        txtMa.requestFocus();
    }
}
