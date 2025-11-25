package vn.edu.stu.quanlydatphong;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

import vn.edu.stu.quanlydatphong.model.DatPhong;
import vn.edu.stu.quanlydatphong.util.FormatUtil;
import vn.edu.stu.quanlydatphong.util.RandomUtil;

public class DatPhongActivity extends AppCompatActivity {

    TextView txtMa, txtNgayDat;
    EditText txtTenNguoiDat, txtSoDem;
    ImageButton btnDatePicker;
    Button btnLuu;
    Calendar calendar;
    DatPhong chon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dat_phong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.datphong), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
        getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("index")) {
            int index = intent.getIntExtra("index",-1);
            chon = DuLieu.layDatPhong(index);
        }
        if(chon != null){
            txtMa.setText(chon.getMa());
            calendar.setTime(chon.getNgayDat());
            txtTenNguoiDat.setText(chon.getTenNguoiDat());
            txtNgayDat.setText(FormatUtil.formatDate(chon.getNgayDat()));
            txtSoDem.setText(chon.getSoDem()+"");
        }else{
            txtMa.setText(RandomUtil.getAlphaNumericString(8));
            txtTenNguoiDat.requestFocus();
            calendar.add(Calendar.DATE,2);
        }
    }

    private void addEvents() {
    }

    private void addControls() {
        txtMa = findViewById(R.id.txtMa);
        txtNgayDat = findViewById(R.id.txtNgayDat);
        txtTenNguoiDat = findViewById(R.id.txtTenNguoiDat);
        txtSoDem = findViewById(R.id.txtSoDem);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnLuu = findViewById(R.id.btnLuu);
        calendar = Calendar.getInstance();
        chon = null;
    }
}