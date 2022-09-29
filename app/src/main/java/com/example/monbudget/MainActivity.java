package com.example.monbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import AccessPersistence.DepenseFixeDBAdapterBU;


public class MainActivity extends AppCompatActivity {
    private Intent intentComptes;
    private Intent intentBilanActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidgets();
    }

    private void setWidgets() {
    }

    public void onMesComptes(View view) {
        intentComptes = new Intent(MainActivity.this, Comptes.class);
        startActivity(intentComptes);
    }

    public void onBilan(View view) {
        intentBilanActivity = new Intent(MainActivity.this, BilanActivity.class);
        startActivity(intentBilanActivity);
    }

}