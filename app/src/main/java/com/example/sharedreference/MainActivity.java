package com.example.sharedreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton rdoNho, rdoVua, rdoTo;
    EditText txtTongSoBai, txtTenTep, txtDong1, txtDong2, txtDong3;
    Button btnLuuVao, btnDocLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    void addControls() {
        radioGroup = findViewById(R.id.radioGroup);
        rdoNho = findViewById(R.id.rdoNho);
        rdoVua = findViewById(R.id.rdoVua);
        rdoTo = findViewById(R.id.rdoTo);

        txtTongSoBai = findViewById(R.id.txtTongSoBai);
        txtTenTep = findViewById(R.id.txtTenTep);

        txtDong1 = findViewById(R.id.txtDong1);
        txtDong2 = findViewById(R.id.txtDong2);
        txtDong3 = findViewById(R.id.txtDong3);

        btnLuuVao = findViewById(R.id.btnLuuVao);
        btnDocLai = findViewById(R.id.btnDocLai);
    }

    void addEvents() {
        btnLuuVao.setOnClickListener(v -> saveData());
        btnDocLai.setOnClickListener(v -> loadData());
    }

    void saveData() {

        // Determine size
        String size = "nhỏ";
        if (rdoVua.isChecked()) size = "vừa";
        if (rdoTo.isChecked()) size = "to";

        // Save SharedPreferences
        SharedPreferences pref = getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("size", size);
        editor.putInt("tongso", Integer.parseInt(txtTongSoBai.getText().toString()));
        editor.putString("tentep", txtTenTep.getText().toString());
        editor.apply();

        // Save file TXT
        String filename = txtTenTep.getText().toString() + ".txt";
        String content = txtDong1.getText().toString() + "\n" +
                txtDong2.getText().toString() + "\n" +
                txtDong3.getText().toString();

        FileUtils.taoFileTXT(content, filename, this);

        Toast.makeText(this, "Đã lưu!", Toast.LENGTH_SHORT).show();
    }

    void loadData() {
        SharedPreferences pref = getSharedPreferences("DATA", MODE_PRIVATE);

        String size = pref.getString("size", "nhỏ");
        int tongso = pref.getInt("tongso", 0);
        String tentep = pref.getString("tentep", "");

        // Restore UI
        if (size.equals("nhỏ")) rdoNho.setChecked(true);
        else if (size.equals("vừa")) rdoVua.setChecked(true);
        else rdoTo.setChecked(true);

        txtTongSoBai.setText(String.valueOf(tongso));
        txtTenTep.setText(tentep);

        // Read file
        String filename = tentep + ".txt";
        String data = FileUtils.getTextFromFile(filename, this);

        String[] lines = data.split("\n");

        txtDong1.setText(lines.length > 0 ? lines[0] : "");
        txtDong2.setText(lines.length > 1 ? lines[1] : "");
        txtDong3.setText(lines.length > 2 ? lines[2] : "");
    }
}
