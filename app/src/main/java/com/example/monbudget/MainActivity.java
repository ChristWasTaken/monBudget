package com.example.monbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent intentComptes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onMesComptes(View view) {
        intentComptes = new Intent(MainActivity.this, Comptes.class);
        startActivity(intentComptes);
        //todo ajouter intent avec retour
    }
}