package vn.edu.stu.quanlysinhvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.edu.stu.quanlysinhvien.model.Sinhvien;


public class MainActivity extends AppCompatActivity {

    // 🔥 Thay bằng IPv4 Address của bạn, vd: 192.168.1.154
    final String SERVER = "http://192.168.1.154/ws/api.php";

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
        btnSua = findViewById(R.id.btnSua);
        txtMasv = findViewById(R.id.txtMaSv);
        txtTensv = findViewById(R.id.txtTenSv);
        btnLuu = findViewById(R.id.btnLuu);
        lvSV = findViewById(R.id.lvSv);

        dssv = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, dssv);
        lvSV.setAdapter(adapter);
    }

    private void addEvents() {
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capNhatSinhVien();
            }
        });
        // Nhấn LƯU: nếu svDangChon = null → thêm, ngược lại → cập nhật
        btnLuu.setOnClickListener(v -> {
            if (svDangChon == null) {
                themSinhVien();
            }
        });

        // CLICK NGẮN – hiện thông tin lên EditText để sửa
        lvSV.setOnItemClickListener((parent, view, position, id) -> {
            svDangChon = dssv.get(position);
            txtMasv.setText(String.valueOf(svDangChon.getMasv()));
            txtTensv.setText(svDangChon.getTensv());
            txtMasv.setEnabled(false);
        });

        // NHẤN GIỮ – XÓA SINH VIÊN
        lvSV.setOnItemLongClickListener((parent, view, position, id) -> {
            Sinhvien sv = dssv.get(position);
            xoaSinhVien(sv.getMasv());
            return true;
        });
    }

    // ---------------- HIỂN THỊ DANH SÁCH ----------------
    private void hienThiDanhSach() {
        String url = SERVER + "?action=getall";

        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        dssv.clear();
                        JSONArray arr = new JSONArray(response);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject o = arr.getJSONObject(i);
                            dssv.add(new Sinhvien(
                                    o.getInt("masv"),
                                    o.getString("tensv")));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(this, "Lỗi JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Không tải được danh sách", Toast.LENGTH_SHORT).show()
        );

        queue.add(req);
    }

    // ---------------- THÊM SINH VIÊN ----------------
    private void themSinhVien() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Xác nhận thêm")
                .setMessage("Bạn có chắc muốn thêm sinh viên này không?")
                .setCancelable(false)
                .setPositiveButton("Thêm", (dialog, which) -> {

                    int masv = Integer.parseInt(txtMasv.getText().toString());
                    String tensv = txtTensv.getText().toString();

                    String url = SERVER + "?action=insert&masv=" + masv + "&tensv=" + tensv;

                    StringRequest req = new StringRequest(Request.Method.GET, url,
                            response -> {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if (obj.getBoolean("message")) {
                                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                                        // reset form
                                        txtMasv.setText("");
                                        txtTensv.setText("");

                                        hienThiDanhSach();
                                    } else {
                                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(this, "Lỗi xử lý JSON", Toast.LENGTH_SHORT).show();
                                }
                            },
                            error -> Toast.makeText(this, "Không thể kết nối server", Toast.LENGTH_SHORT).show()
                    );

                    queue.add(req);
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // ---------------- XÓA SINH VIÊN ----------------
    private void xoaSinhVien(int masv) {

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa sinh viên có mã: " + masv + " ?")
                .setCancelable(false)
                .setPositiveButton("Xóa", (dialog, which) -> {

                    String url = SERVER + "?action=delete&masv=" + masv;

                    StringRequest req = new StringRequest(Request.Method.GET, url,
                            response -> {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if (obj.getBoolean("message")) {
                                        Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
                                        hienThiDanhSach();
                                    } else {
                                        Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(this, "Lỗi xử lý JSON", Toast.LENGTH_SHORT).show();
                                }
                            },
                            error -> Toast.makeText(this, "Không thể kết nối server", Toast.LENGTH_SHORT).show()
                    );

                    queue.add(req);
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }


    // ---------------- CẬP NHẬT SINH VIÊN ----------------
    private void capNhatSinhVien() {
        new AlertDialog.Builder(MainActivity.this).setTitle("Xác nhận sửa")
                .setMessage("Bạn có chắc muốn cập nhật sinh viên này không?")
                .setCancelable(false)
                .setPositiveButton("Cập nhật", (dialog, which) -> {

                    int masv = svDangChon.getMasv();
                    if(masv <= 0){
                        Toast.makeText(this, "Mã sinh viên không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String tensv = txtTensv.getText().toString();
                    if(tensv.isEmpty()){
                        Toast.makeText(this, "Tên sinh viên không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    String url = SERVER + "?action=update&masv=" + masv + "&tensv=" + tensv;

                    StringRequest req = new StringRequest(Request.Method.GET, url,
                            response -> {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if (obj.getBoolean("message")) {
                                        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                                        // reset form
                                        txtMasv.setText("");
                                        txtTensv.setText("");
                                        txtMasv.setEnabled(true);
                                        svDangChon = null;

                                        hienThiDanhSach();
                                    } else {
                                        Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(this, "Lỗi xử lý JSON", Toast.LENGTH_SHORT).show();
                                }
                            },
                            error -> Toast.makeText(this, "Không thể kết nối server", Toast.LENGTH_SHORT).show()
                    );

                    queue.add(req);
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
