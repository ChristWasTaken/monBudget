package com.example.monbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import AccessPersistence.DepenseFixeDBAdapterBU;


public class MainActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidgets();
    }

    private void setWidgets() {
    }

    public void onMesComptes(View view) {
        intent = new Intent(MainActivity.this, ComptesActivity.class);
        startActivity(intent);
    }

    public void onBilan(View view) {
        intent = new Intent(MainActivity.this, BilanActivity.class);
        startActivity(intent);
    }

    public void onMesRevenues(View view) {
        intent = new Intent(MainActivity.this, RevenuesActivity.class);
        startActivity(intent);
    }

    public void onDepenses(View view) {
        intent = new Intent(MainActivity.this, DepensesActivity.class);
        startActivity(intent);
    }

    public void onCalendrier(View view) {
    }
}