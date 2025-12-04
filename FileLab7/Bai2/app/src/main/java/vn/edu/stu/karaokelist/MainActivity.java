package vn.edu.stu.karaokelist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import vn.edu.stu.karaokelist.adapter.BaiHatAdapter;
import vn.edu.stu.karaokelist.model.BaiHat;
import vn.edu.stu.karaokelist.util.AppDatabase;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    EditText txtTimKiem;
    ListView lvBaiHat;
    ArrayList<BaiHat> dsBaiHat;
    TextWatcher textWatcher;
    BaiHatAdapter adapter;
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
        hienThiDanhSachBaiHat("");
    }

    private void hienThiDanhSachBaiHat(String keyword) {
        dsBaiHat.clear();

        if (keyword == null || keyword.trim().isEmpty()) {
            dsBaiHat.addAll(db.baiHatDao().getAll());  // hiển thị tất cả
        } else {
            dsBaiHat.addAll(db.baiHatDao().search(keyword)); // tìm kiếm theo keyword
        }

        adapter.notifyDataSetChanged();
    }

    private void addControls() {
        db = AppDatabase.getAppDatabase(this);
        txtTimKiem = findViewById(R.id.txtTimKiem);
        lvBaiHat = findViewById(R.id.lvBaiHat);
        dsBaiHat = new ArrayList<>();
        adapter = new BaiHatAdapter(MainActivity.this, R.layout.item_baihat, dsBaiHat);
        lvBaiHat.setAdapter(adapter);
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                hienThiDanhSachBaiHat(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        txtTimKiem.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hienThiDanhSachBaiHat(txtTimKiem.getText().toString());

    }
}