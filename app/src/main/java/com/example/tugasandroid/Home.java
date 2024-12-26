package com.example.tugasandroid;

import android.content.Intent;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    private TextView  nama, divisi;
    private ImageView agenda, tugas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nama = findViewById(R.id.tvNama);
        divisi = findViewById(R.id.tvDivisi);
        agenda = findViewById(R.id.agenda);
        tugas = findViewById(R.id.tugas);

        tugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, com.example.tugasandroid.tugas.class);
                startActivity(intent);
            }
        });
        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, agenda.class);
                startActivity(intent);
            }
        });

    }
}