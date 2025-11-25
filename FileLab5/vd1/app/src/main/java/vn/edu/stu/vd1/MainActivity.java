package vn.edu.stu.vd1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

import vn.edu.stu.vd1.model.Congviec;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabThem;
    ArrayAdapter<Congviec> adapter;
    ListView lvCongviec;
    Congviec chon;

    // Cần khai báo ArrayList<Congviec> để quản lý dữ liệu
    ArrayList<Congviec> data = new ArrayList<>();

    int requestCode = 113, resultCode = 115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tạo một vài công việc mẫu để hiển thị (TÙY CHỌN)
        // data.add(new Congviec("Học lập trình Android", "10/11/2025"));
        // data.add(new Congviec("Làm bài tập lớn", "15/11/2025"));

        addControls();
        addEvents();
    }

    private void addControls() {
        fabThem = findViewById(R.id.fabThem);

        // Khởi tạo adapter sử dụng ArrayList data
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                data // Sử dụng ArrayList data để liên kết với adapter
        );

        lvCongviec = findViewById(R.id.lvCongviec);
        lvCongviec.setAdapter(adapter);
        chon = null;
    }

    private void addEvents() {
        // 1. Sự kiện Thêm (nhấn nút FAB)
        fabThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        ThemSuaCongviecActivity.class
                );
                startActivityForResult(intent, requestCode);
            }
        });

        // 2. Sự kiện Sửa (nhấn giữ item)
        lvCongviec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < adapter.getCount()) {
                    Intent intent = new Intent(
                            MainActivity.this,
                            ThemSuaCongviecActivity.class
                    );
                    chon = adapter.getItem(position);
                    intent.putExtra("CHON", chon);
                    startActivityForResult(intent, requestCode);
                }
            }
        });

        // 3. Sự kiện XÓA (nhấn giữ lâu item) - ĐÃ BỔ SUNG CODE
        lvCongviec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                // Lấy công việc cần xóa
                final Congviec congViecCanXoa = adapter.getItem(position);

                // Hiển thị Dialog xác nhận
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc muốn xóa công việc: " + congViecCanXoa.getTen() + "?");

                // Thiết lập nút "Đồng ý"
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xóa công việc khỏi danh sách data
                        data.remove(congViecCanXoa);

                        // Cập nhật lại giao diện ListView
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                // Thiết lập nút "Hủy"
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true; // Trả về true để tiêu thụ sự kiện (không kích hoạt sự kiện OnItemClick)
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.requestCode) {
            if (resultCode == this.resultCode) {
                if (data.hasExtra("TRA")) {
                    Congviec tra = (Congviec) data.getSerializableExtra("TRA");

                    if (chon == null) {
                        // Trường hợp THÊM MỚI
                        adapter.add(tra); // Thêm vào ArrayList data
                    } else {
                        // Trường hợp SỬA
                        chon.setTen(tra.getTen());
                        chon.setHan(tra.getHan());
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
        chon = null; // Quan trọng: Đặt lại chon về null sau khi xử lý xong
    }
}