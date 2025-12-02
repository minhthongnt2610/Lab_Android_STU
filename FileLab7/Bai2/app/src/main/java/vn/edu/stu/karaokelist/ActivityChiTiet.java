package vn.edu.stu.karaokelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import vn.edu.stu.karaokelist.model.BaiHat;
import vn.edu.stu.karaokelist.util.AppDatabase;

public class ActivityChiTiet extends AppCompatActivity {

    TextView txtMa, txtTen, txtTacGia, txtTheLoai, txtLoi, txtTrangThai;
    ImageButton btnBack;

    AppDatabase db;
    BaiHat baiHat;
    String maBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        db = AppDatabase.getAppDatabase(this);

        addControls();
        getDataIntent();
        loadData();
        addEvents();
    }

    private void addControls() {
        txtMa = findViewById(R.id.txtCTMa);
        txtTen = findViewById(R.id.txtCTTen);
        txtTacGia = findViewById(R.id.txtCTTacGia);
        txtTheLoai = findViewById(R.id.txtCTTheLoai);
        txtLoi = findViewById(R.id.txtCTLoi);
        txtTrangThai = findViewById(R.id.txtCTYeuThich);

        btnBack = findViewById(R.id.btnBack);
    }

    private void getDataIntent() {
        maBH = getIntent().getStringExtra("MABH");
    }

    private void loadData() {
        baiHat = db.baiHatDao().findById(maBH);

        if (baiHat != null) {
            txtMa.setText(baiHat.getMaBH());
            txtTen.setText(baiHat.getTenBH());
            txtTacGia.setText(baiHat.getTacGia());
            txtTheLoai.setText(baiHat.getTheLoai());
            txtLoi.setText(baiHat.getLoiBH());

            // Yêu thích: 1 = thích, còn lại = không thích
            Integer yt = baiHat.getYeuThich();
            boolean isThich = (yt != null && yt == 1);
            txtTrangThai.setText(isThich ? "Yêu thích: Có" : "Yêu thích: Không");
        }
    }

    private void addEvents() {
        btnBack.setOnClickListener(view -> finish());
    }
}
