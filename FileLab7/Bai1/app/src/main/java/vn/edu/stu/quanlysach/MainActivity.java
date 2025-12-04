package vn.edu.stu.quanlysach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Insert;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.edu.stu.quanlysach.model.Sach;
import vn.edu.stu.quanlysach.util.AppDatabase;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    ArrayList<Sach> dsSach;
    ArrayAdapter<Sach> adapter;
    ListView lvSach;
    FloatingActionButton fabThem;

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
        hienThiDanhSachSach();
    }

    private void hienThiDanhSachSach() {
        dsSach.clear();
        dsSach.addAll(db.sachDao().getAll());
        adapter.notifyDataSetChanged();
    }

    private void addEvents() {
        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position >= 0 && position < dsSach.size()) {
                    Sach sach = dsSach.get(position);
                    new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("Xóa sách")
                            .setMessage("Bạn có chắc muốn xóa cuốn \"" + sach.getTen() + "\" không?")
                            .setPositiveButton("Xóa", (dialog, which) -> {
                                int kq = db.sachDao().delete(sach);
                                if (kq > 0) {
                                    dsSach.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Hủy", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                }
                return true;
            }
        });

        fabThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        MainActivity.this,
                        themActivity.class
                );
                startActivity(intent);
            }
        });
        lvSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Sach sach = dsSach.get(position);

                Intent intent = new Intent(MainActivity.this, Edit_Activity.class);
                intent.putExtra("MASACH", sach.getMa());   // truyền mã sách
                startActivity(intent);
            }
        });

    }

    private void addControls() {
        db = AppDatabase.getAppDatabase(this);
        lvSach = findViewById(R.id.lvSach);
        fabThem = findViewById(R.id.fabThem);
        dsSach = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                dsSach
        );
        lvSach.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hienThiDanhSachSach();  // reload list khi trở lại màn hình chính
    }

}