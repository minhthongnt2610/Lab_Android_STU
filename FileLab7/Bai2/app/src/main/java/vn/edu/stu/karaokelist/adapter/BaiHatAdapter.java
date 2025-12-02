package vn.edu.stu.karaokelist.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.edu.stu.karaokelist.ActivityChiTiet;
import vn.edu.stu.karaokelist.R;
import vn.edu.stu.karaokelist.model.BaiHat;
import vn.edu.stu.karaokelist.util.AppDatabase;

public class BaiHatAdapter extends ArrayAdapter<BaiHat> {

    Activity context;
    int resource;
    List<BaiHat> objects;

    public BaiHatAdapter(Activity context, int resource, List<BaiHat> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView txtMaBH = item.findViewById(R.id.txtMaBH);
        TextView txtTenBH = item.findViewById(R.id.txtTenBH);
        TextView txtTacGia = item.findViewById(R.id.txtTacGia);

        final ImageButton btnThich = item.findViewById(R.id.btnThich);
        final ImageButton btnBoThich = item.findViewById(R.id.btnBoThich);
        final ImageButton btnChiTiet = item.findViewById(R.id.btnChiTiet);
        final ImageButton btnXoa = item.findViewById(R.id.btnXoa);

        final BaiHat baiHat = this.objects.get(position);

        txtMaBH.setText(baiHat.getMaBH());
        txtTenBH.setText(baiHat.getTenBH());
        txtTacGia.setText(baiHat.getTacGia());

        Integer yt = baiHat.getYeuThich();
        boolean isYeuThich = (yt != null && yt == 1);

        if (isYeuThich) {
            btnThich.setVisibility(View.INVISIBLE);
            btnBoThich.setVisibility(View.VISIBLE);
        } else {
            btnThich.setVisibility(View.VISIBLE);
            btnBoThich.setVisibility(View.INVISIBLE);
        }

        btnThich.setOnClickListener(v -> xulyThich(baiHat, btnThich, btnBoThich));

        btnBoThich.setOnClickListener(v -> xulyBoThich(baiHat, btnThich, btnBoThich));

        btnChiTiet.setOnClickListener(v -> xulyChiTiet(baiHat));

        btnXoa.setOnClickListener(v -> xulyXoa(baiHat));

        return item;
    }

    private void xulyXoa(BaiHat baiHat) {

        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa bài hát \"" + baiHat.getTenBH() + "\" không?")
                .setPositiveButton("Xóa", (dialog, which) -> {

                    AppDatabase db = AppDatabase.getAppDatabase(context);
                    int ret = db.baiHatDao().delete(baiHat);

                    if (ret > 0) {
                        objects.remove(baiHat);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa bài hát thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Xóa bài hát thất bại", Toast.LENGTH_SHORT).show();
                    }

                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss(); // chỉ đóng dialog
                })
                .show();
    }


    private void xulyBoThich(BaiHat baiHat, ImageButton btnThich, ImageButton btnBoThich) {
        AppDatabase db = AppDatabase.getAppDatabase(this.context);

        baiHat.setYeuThich(0);
        int ret = db.baiHatDao().update(baiHat);

        if (ret > 0) {
            Toast.makeText(context, "Bỏ yêu thích thành công", Toast.LENGTH_SHORT).show();
            btnThich.setVisibility(View.VISIBLE);
            btnBoThich.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(context, "Lỗi khi cập nhật", Toast.LENGTH_SHORT).show();
        }
    }

    private void xulyChiTiet(BaiHat baiHat) {
        // TODO: mở activity chi tiết tại đây
        Intent intent = new Intent(context, ActivityChiTiet.class);
        intent.putExtra("MABH", baiHat.getMaBH());
        context.startActivity(intent);
    }

    private void xulyThich(BaiHat baiHat, ImageButton btnThich, ImageButton btnBoThich) {
        AppDatabase db = AppDatabase.getAppDatabase(this.context);

        baiHat.setYeuThich(1); // 1 = thích
        int ret = db.baiHatDao().update(baiHat);

        if (ret > 0) {
            Toast.makeText(context, "Gán yêu thích thành công", Toast.LENGTH_SHORT).show();
            btnThich.setVisibility(View.INVISIBLE);
            btnBoThich.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(context, "Lỗi khi cập nhật", Toast.LENGTH_SHORT).show();
        }
    }
}
