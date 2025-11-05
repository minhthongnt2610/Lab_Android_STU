package vn.edu.stu.danh_sach_lop;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] arrDsLop;
    ArrayAdapter<String> adapter;
    ListView lvDsLop;
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

    private void addEvents() {
        lvDsLop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(
                        MainActivity.this,
                        "Bạn nhấn một cái tại " + arrDsLop[position],
                        Toast.LENGTH_LONG
                ).show();
            }
        });
        lvDsLop.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(
                        MainActivity.this,
                        "Bạn nhấn lâu tại " + arrDsLop[position],
                        Toast.LENGTH_LONG
                ).show();
                return true;
            }
        });
    }

    private void addControls() {
        lvDsLop = findViewById(R.id.lvDsLop);
        arrDsLop = getResources().getStringArray(R.array.arr_ds_lop);
        adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_single_choice,
                arrDsLop
        );
        lvDsLop.setAdapter(adapter);
    }
}