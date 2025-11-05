package vn.edu.stu.tuy_bien_giao_dien.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.edu.stu.tuy_bien_giao_dien.R;
import vn.edu.stu.tuy_bien_giao_dien.model.Nhanvien;

public class NhanvienAdapter extends ArrayAdapter<Nhanvien> {
    Activity context;
    int resource;
    List<Nhanvien> objects;
    public NhanvienAdapter(Activity context, int resource, List<Nhanvien> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource,null);
        TextView txtMa = item.findViewById(R.id.txtMa);
        TextView txtTen = item.findViewById(R.id.txtTen);
        TextView txtSdt = item.findViewById(R.id.txtSdt);
        Nhanvien nv = this.objects.get(position);
        txtMa.setText(nv.getMa());
        txtTen.setText(nv.getTen());
        txtSdt.setText(nv.getSdt());
        return item;
    }
}
