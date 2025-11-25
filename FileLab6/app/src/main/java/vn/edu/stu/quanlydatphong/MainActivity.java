package vn.edu.stu.quanlydatphong;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import vn.edu.stu.quanlydatphong.model.DatPhong;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<DatPhong> adapter;
    ListView lvDatPhong;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void addControls() {
        lvDatPhong = findViewById(R.id.lvDatPhong);
        fabThem = findViewById(R.id.fabThem);

        adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, DuLieu.dsDatPhong);
        lvDatPhong.setAdapter(adapter);
        registerForContextMenu(lvDatPhong);
    }

    private void addEvents() {
        fabThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DatPhongActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnuThem) {
            Intent intent = new Intent(MainActivity.this, DatPhongActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.mnuThoat) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.lvDatPhong) {
            getMenuInflater().inflate(R.menu.mnu_datphong, menu);
        }
    }

    @Override
    public boolean onContextItemSelected( MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        int id = item.getItemId();
        if (id == R.id.mnuSua) {
            Intent intent = new Intent(MainActivity.this, DatPhongActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
            return true;
        } else if (id == R.id.mnuXoa) {
            DuLieu.dsDatPhong.remove(index);
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}