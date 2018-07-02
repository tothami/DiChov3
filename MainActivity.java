package com.example.admin.dicho;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.admin.dicho.R.id.editTextTenTD;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView lvThucDon;
    ArrayList<ThucDon> arrayThucDon;
    ThucDonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvThucDon = (ListView) findViewById(R.id.listviewThucDon);
        arrayThucDon = new ArrayList<>();

        adapter = new ThucDonAdapter(this, R.layout.dong_thuc_don, arrayThucDon);
        lvThucDon.setAdapter(adapter);

        database = new Database(this, "tiencho.sqlite", null, 1);

        database.QueryData("CREATE TABLE IF NOT EXISTS ThucDon(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenTD VARCHAR(200), GiaTD VARCHAR(50))");

        //database.QueryData("INSERT INTO ThucDon VALUES(null, 'Thịt nách', '94000')");

        GetDataThucDon();

    }

    public void DialogXoaTD(final String tentd, final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa "+ tentd +" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM ThucDon WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Đã xóa "+ tentd, Toast.LENGTH_SHORT).show();
                GetDataThucDon();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }

    public void DialogSuaThucDon(final String ten, String gia, final int id){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);

        final EditText edtTenTD = (EditText) dialog.findViewById(R.id.editTextTenTdEdit);
        final EditText edtGiaTD = (EditText) dialog.findViewById(R.id.editTextGiaTdEdit);
        Button btnXacNhan = (Button) dialog.findViewById(R.id.buttonXacNhan);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuyEdit);

        edtTenTD.setText(ten);
        edtGiaTD.setText(gia);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtTenTD.getText().toString().trim();
                String giaMoi = edtGiaTD.getText().toString().trim();
                database.QueryData("UPDATE ThucDon SET TenTD = '"+tenMoi+"', GiaTD = '"+giaMoi+"' WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Đã cập nhật "+ten, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataThucDon();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void GetDataThucDon(){
        Cursor dataThucDon = database.GetData("SELECT * FROM ThucDon");
        arrayThucDon.clear();
        int tong = 0;
        while (dataThucDon.moveToNext()){
            int id = dataThucDon.getInt(0);
            String ten = dataThucDon.getString(1);
            String gia = dataThucDon.getString(2);
            int tien = Integer.parseInt(gia);
            tong = tong + tien;
            arrayThucDon.add(new ThucDon(id, ten, gia));
        }
        String tota = tong + "";
        Toast.makeText(this, "Tổng tiền: " + tota, Toast.LENGTH_SHORT).show();

        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_thucdon, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menuAdd){
            DialogThem();
        }

        return super.onOptionsItemSelected(item);
    }

    private void DialogThem(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_thuc_don);

        final EditText edtTen = (EditText) dialog.findViewById(R.id.editTextTenTD);
        final EditText edtGia = (EditText) dialog.findViewById(R.id.editTextGiaTD);
        Button btnThem = (Button) dialog.findViewById(R.id.buttonThem);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tentd = edtTen.getText().toString();
                if(tentd.equals("")){
                    Toast.makeText(MainActivity.this, "Bạn vui lòng nhập tên hàng và giá", Toast.LENGTH_SHORT).show();
                }else{
                    String giatd = edtGia.getText().toString().trim();
                    database.QueryData("INSERT INTO ThucDon VALUES(null, '"+tentd+"', '"+giatd+"')");
                    Toast.makeText(MainActivity.this, "Đã thêm "+ tentd, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataThucDon();

                }
            }
        });

        dialog.show();
    }
}
