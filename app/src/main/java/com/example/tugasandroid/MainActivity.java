package com.example.tugasandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    private EditText textEmail, textPassword;
    private TextView loginBtn, daftarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textEmail = findViewById(R.id.text_email);
        textPassword = findViewById(R.id.text_password);
        loginBtn = findViewById(R.id.btn_login);
        daftarBtn = findViewById(R.id.btn_buatakun);

        // Fungsi buat daftar
        daftarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke activity register
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        // Fungsi untuk login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();

                // Validasi input kosong
                if (TextUtils.isEmpty(email)) {
                    textEmail.setError("Email harus diisi");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    textPassword.setError("Password harus diisi");
                    return;
                }

                // Proses login
                loginUser(email, password);
            }
        });
    }

    private void loginUser(String email, String password) {
        // URL backend untuk login
        String url = "https://ac38-103-111-210-130.ngrok-free.app/dbandroid/login.php";

        // Request login ke backend
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success_admin")) {
                            Toast.makeText(MainActivity.this, "Login berhasil sebagai Admin!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        } else if (response.equals("success_user")) {
                            Toast.makeText(MainActivity.this, "Login berhasil sebagai User!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Gagal: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        // Menambahkan request ke antrian Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
