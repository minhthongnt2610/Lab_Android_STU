package vn.edu.stu.vd1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import vn.edu.stu.vd1.model.Congviec;
import vn.edu.stu.vd1.util.FormatUtil;

public class ThemSuaCongviecActivity extends AppCompatActivity {

    EditText txtTen;
    TextView txtNgay, txtGio;
    ImageButton btnDatePicker, btnTimePicker;
    Button btnLuu;
    Calendar calendar;
    Congviec chon;
    int resultCode = 115; // Phải trùng với resultCode trong MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_congviec2);

        addControls();
        getIntentData();
        addEvents();
    }

    private void addControls() {
        txtTen = findViewById(R.id.txtTen);
        txtNgay = findViewById(R.id.txtNgay);
        txtGio = findViewById(R.id.txtGio);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnTimePicker = findViewById(R.id.btnTimePicker);
        btnLuu = findViewById(R.id.btnLuu);
        calendar = Calendar.getInstance();
        chon = null;
    }

    private void getIntentData() {
        // Lấy Intent gọi tôi
        Intent intent = getIntent();

        // Kiểm tra có biến CHON trong intent gọi tôi không?
        if (intent.hasExtra("CHON")) {
            // Có biến CHON gửi tới, kiểm tra có NULL hay không?
            // Nếu NULL nghĩa là không có chọn -> Thêm mới.
            // Ngược lại, là chỉnh sửa
            chon = (Congviec) intent.getSerializableExtra("CHON");
            if (chon != null) {
                txtTen.setText(chon.getTen());

                // Giả định Congviec có phương thức getHan() trả về Date hoặc String
                // và ta cần chuyển nó thành Calendar để thiết lập giao diện
                // Ở đây, ta dùng thời gian hiện tại của Calendar để giả lập
                calendar.setTime(new Date());

                txtNgay.setText(FormatUtil.formatDate(calendar.getTime()));
                txtGio.setText(FormatUtil.formatTime(calendar.getTime()));
            } else {
                resetView();
            }
        } else {
            resetView();
        }
    }

    private void resetView() {
        txtTen.setText("");
        txtNgay.setText("dd/MM/yyyy");
        txtGio.setText("hh:mm aa");
        calendar = Calendar.getInstance();
        chon = null;
    }

    private void addEvents() {
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyChonNgay();
            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyChonGio();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luuCongViec();
            }
        });
    }

    // --- CÁC HÀM XỬ LÝ SỰ KIỆN ĐÃ ĐƯỢC BỔ SUNG ---

    private void xulyChonNgay() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtNgay.setText(FormatUtil.formatDate(calendar.getTime()));
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ThemSuaCongviecActivity.this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void xulyChonGio() {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                txtGio.setText(FormatUtil.formatTime(calendar.getTime()));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                ThemSuaCongviecActivity.this,
                listener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false // Format 12h
        );
        timePickerDialog.show();
    }

    private void luuCongViec() {
        String tenCongViec = txtTen.getText().toString();

        // Tạo đối tượng Congviec mới để truyền về MainActivity
        Congviec congViecMoi;

        if (chon == null) {
            // Thêm mới: Tạo đối tượng mới hoàn toàn
            congViecMoi = new Congviec( calendar.getTime(),tenCongViec);
        } else {
            // Chỉnh sửa: Sử dụng đối tượng cũ, cập nhật giá trị
            congViecMoi = chon;
            congViecMoi.setTen(tenCongViec);
            congViecMoi.setHan(calendar.getTime());
        }

        // Tạo Intent để truyền dữ liệu ngược về MainActivity
        Intent intent = new Intent();
        intent.putExtra("TRA", congViecMoi);

        // Thiết lập kết quả và đóng Activity
        setResult(resultCode, intent);
        finish();
    }
}