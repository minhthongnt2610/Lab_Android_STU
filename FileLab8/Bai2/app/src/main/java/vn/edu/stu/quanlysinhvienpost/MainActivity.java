package vn.edu.stu.quanlysinhvienpost;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.stu.quanlysinhvienpost.model.Sinhvien;


public class MainActivity extends AppCompatActivity {

    final String SERVER = "http://192.168.1.154/ws/api_post.php";

    EditText txtMasv, txtTensv;
    Button btnLuu, btnSua;
    ListView lvSV;

    ArrayList<Sinhvien> dssv;
    ArrayAdapter<Sinhvien> adapter;
    Sinhvien svDangChon = null;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        addControls();
        addEvents();
        hienThiDanhSach();
    }

    private void addControls() {
        txtMasv = findViewById(R.id.txtMaSv);
        txtTensv = findViewById(R.id.txtTenSv);
        btnLuu = findViewById(R.id.btnLuu);
        btnSua = findViewById(R.id.btnSua);
        lvSV = findViewById(R.id.lvSv);

        dssv = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dssv);
        lvSV.setAdapter(adapter);
    }

    private void addEvents() {

        // Lưu sinh viên
        btnLuu.setOnClickListener(v -> dialogLuu());

        // Sửa sinh viên
        btnSua.setOnClickListener(v -> {
            if (svDangChon != null)
                dialogSua();
            else
                Toast.makeText(this, "Hãy chọn sinh viên để sửa", Toast.LENGTH_SHORT).show();
        });

        // Click để hiển thị thông tin lên form
        lvSV.setOnItemClickListener((parent, view, position, id) -> {
            svDangChon = dssv.get(position);
            txtMasv.setText(String.valueOf(svDangChon.getMasv()));
            txtTensv.setText(svDangChon.getTensv());
            txtMasv.setEnabled(false);
        });

        // Long click để xóa
        lvSV.setOnItemLongClickListener((parent, view, position, id) -> {
            Sinhvien sv = dssv.get(position);
            dialogXoa(sv.getMasv());
            return true;
        });
    }

    // =====================================================================
    // HIỂN THỊ DANH SÁCH
    // =====================================================================
    private void hienThiDanhSach() {

        StringRequest req = new StringRequest(Request.Method.POST, SERVER,
                response -> {
                    try {
                        dssv.clear();
                        JSONArray arr = new JSONArray(response);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject o = arr.getJSONObject(i);
                            dssv.add(new Sinhvien(
                                    o.getInt("masv"),
                                    o.getString("tensv")
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(this, "Lỗi JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Không tải được danh sách", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("action", "getall");
                return map;
            }
        };

        queue.add(req);
    }


    // =====================================================================
    // DIALOG XÁC NHẬN LƯU
    // =====================================================================
    private void dialogLuu() {

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận thêm")
                .setMessage("Bạn có muốn thêm sinh viên này?")
                .setPositiveButton("Lưu", (dialog, which) -> themSinhVien())
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // =====================================================================
    // DIALOG XÁC NHẬN SỬA
    // =====================================================================
    private void dialogSua() {

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận sửa")
                .setMessage("Bạn có muốn cập nhật sinh viên này?")
                .setPositiveButton("Sửa", (dialog, which) -> capNhatSinhVien())
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // =====================================================================
    // DIALOG XÁC NHẬN XÓA
    // =====================================================================
    private void dialogXoa(int masv) {

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa sinh viên mã " + masv + "?")
                .setPositiveButton("Xóa", (dialog, which) -> xoaSinhVien(masv))
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }


    // =====================================================================
    // THÊM SINH VIÊN (POST)
    // =====================================================================
    private void themSinhVien() {

        int masv;
        try {
            masv = Integer.parseInt(txtMasv.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Mã SV không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        String tensv = txtTensv.getText().toString().trim();

        if (tensv.isEmpty()) {
            Toast.makeText(this, "Tên SV không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest req = new StringRequest(Request.Method.POST, SERVER,
                response -> {
                    try {
                        JSONObject o = new JSONObject(response);
                        if (o.getBoolean("message")) {
                            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            hienThiDanhSach();
                            txtMasv.setText("");
                            txtTensv.setText("");
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Lỗi thêm", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Không thêm được", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("action", "insert");
                map.put("masv", String.valueOf(masv));
                map.put("tensv", tensv);
                return map;
            }
        };

        queue.add(req);
    }

    // =====================================================================
    // SỬA SINH VIÊN (POST)
    // =====================================================================
    private void capNhatSinhVien() {

        int masv = Integer.parseInt(txtMasv.getText().toString());
        String tensv = txtTensv.getText().toString().trim();

        StringRequest req = new StringRequest(Request.Method.POST, SERVER,
                response -> {
                    try {
                        JSONObject o = new JSONObject(response);
                        if (o.getBoolean("message")) {
                            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                            svDangChon = null;
                            txtMasv.setEnabled(true);
                            txtMasv.setText("");
                            txtTensv.setText("");

                            hienThiDanhSach();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Không cập nhật được", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("action", "update");
                map.put("masv", String.valueOf(masv));
                map.put("tensv", tensv);
                return map;
            }
        };

        queue.add(req);
    }

    // =====================================================================
    // XÓA SINH VIÊN (POST)
    // =====================================================================
    private void xoaSinhVien(int masv) {

        StringRequest req = new StringRequest(Request.Method.POST, SERVER,
                response -> {
                    try {
                        JSONObject o = new JSONObject(response);
                        if (o.getBoolean("message")) {
                            Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
                            hienThiDanhSach();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Lỗi JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Không thể xóa", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("action", "delete");
                map.put("masv", String.valueOf(masv));
                return map;
            }
        };

        queue.add(req);
    }
}
