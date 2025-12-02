package vn.edu.stu.quanlydatphong;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
            calendar.add(Calendar.DATE,0);
        }
    }

    private void addEvents() {
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyChonNgay();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLuu();
            }
        });
    }

    private void xuLyLuu() {
        String ten = txtTenNguoiDat.getText().toString().trim();
        String soDemStr = txtSoDem.getText().toString().trim();
        if (ten.isEmpty()) {
            txtTenNguoiDat.setError("Tên người đặt không được bỏ trống!");
            txtTenNguoiDat.requestFocus();
            return;
        }
        if (soDemStr.isEmpty()) {
            txtSoDem.setError("Số đêm không được bỏ trống!");
            txtSoDem.requestFocus();
            return;
        }
        int soDem = Integer.parseInt(soDemStr);
        if (soDem <= 0) {
            txtSoDem.setError("Số đêm phải lớn hơn 0!");
            txtSoDem.requestFocus();
            return;
        }
        if (chon != null) {
            chon.setTenNguoiDat(ten);
            chon.setNgayDat(calendar.getTime());
            chon.setSoDem(soDem);
        } else {
            DatPhong datPhong = new DatPhong();
            datPhong.setMa(txtMa.getText().toString());
            datPhong.setTenNguoiDat(ten);
            datPhong.setNgayDat(calendar.getTime());
            datPhong.setSoDem(soDem);
            DuLieu.themDatPhong(datPhong);
        }

        finish();
    }


    private void xuLyChonNgay() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtNgayDat.setText(FormatUtil.formatDate(calendar.getTime()));
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                DatPhongActivity.this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }


    private void addControls() {
        txtMa = findViewById(R.id.txtMa);
        txtNgayDat = findViewById(R.id.txtNgayDat);
        txtTenNguoiDat = findViewById(R.id.txtTenNguoiDat);
        txtSoDem = findViewById(R.id.txtSoDem);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnLuu = findViewById(R.id.btnLuu);
        calendar = Calendar.getInstance();
        txtNgayDat.setText(FormatUtil.formatDate(calendar.getTime()));
        chon = null;
    }
}