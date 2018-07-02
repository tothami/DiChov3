package com.example.admin.dicho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Admin on 6/28/2018.
 */

public class ThucDonAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<ThucDon> thucDonList;

    public ThucDonAdapter(MainActivity context, int layout, List<ThucDon> thucDonList) {
        this.context = context;
        this.layout = layout;
        this.thucDonList = thucDonList;
    }

    @Override
    public int getCount() {
        return thucDonList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTen, txtGia;
        ImageView imgEdit, imgDelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTen = (TextView) view.findViewById(R.id.textviewTen);
            holder.txtGia = (TextView) view.findViewById(R.id.textviewGia);
            holder.imgEdit = (ImageView) view.findViewById(R.id.imageviewEdit);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imageviewDelete);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final ThucDon thucDon = thucDonList.get(i);
        holder.txtTen.setText(thucDon.getTenTD());
        holder.txtGia.setText(thucDon.getGiaTD());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogSuaThucDon(thucDon.getTenTD(), thucDon.getGiaTD(),thucDon.getIdTD());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogXoaTD(thucDon.getTenTD(), thucDon.getIdTD());
            }
        });

        return view;
    }
}
