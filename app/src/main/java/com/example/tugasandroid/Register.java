package com.example.tugasandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Register extends AppCompatActivity {
    private EditText etEmail, etPassword, etCode;
    private CheckBox ckAdmin;
    private Button btDaftar, btKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Sesuaikan nama file XML

        // Inisialisasi komponen UI
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etCode = findViewById(R.id.etCode);
        ckAdmin = findViewById(R.id.ckAdmin);
        btDaftar = findViewById(R.id.btDaftar);
        btKembali = findViewById(R.id.btKembali);

        // Atur logika untuk checkbox "Sebagai Admin"
        ckAdmin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etCode.setEnabled(false); // Nonaktifkan input unik kode
                etCode.setText("Kode otomatis dibuat untuk Admin"); // Informasi ke pengguna
            } else {
                etCode.setEnabled(true); // Aktifkan kembali input unik kode
                etCode.setText(""); // Bersihkan input jika beralih ke user biasa
            }
        });

        // Tombol "Daftar Sekarang"
        btDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Tombol "Sudah Ada Akun"
        btKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke aktivitas login
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        boolean isAdmin = ckAdmin.isChecked();

        // Validasi input
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan Password wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isAdmin && code.isEmpty()) {
            Toast.makeText(this, "Kode unik wajib diisi untuk user biasa!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isAdmin) {
            code = generateUniqueCode(); // Sistem membuat kode otomatis untuk admin
        }

        String url = "https://ac38-103-111-210-130.ngrok-free.app/dbAndroid/register.php";
        String finalCode = code;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                    if (response.contains("Registrasi berhasil")) {
                        Intent intent = new Intent(Register.this, perofile.class);
                        startActivity(intent);
                        finish();
                    }
                },
                error -> Toast.makeText(Register.this, "Gagal: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("code", finalCode);
                params.put("isAdmin", isAdmin ? "1" : "0");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String generateUniqueCode() {
        // Membuat Unik Kode menggunakan UUID
        return UUID.randomUUID().toString().substring(0, 8); // Potong menjadi 8 karakter
    }
}