package com.example.monbudget;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import AccessPersistence.DepenseFixeDBAdapter;
import model.DepenseFixe;

public class CalendrierActivity extends AppCompatActivity {

    //TODO : Ajouter une application pour les checkbox isPaye et modifier le POJO et BD.. dans une version proche!

    private DepenseFixeDBAdapter depenseFixeDBAdapter;
    private List<DepenseFixe> listDepensesFixes;
    private Intent intent;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);
        // Ajout du back button dans l'action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Récupération de la liste des dépenses fixes
        depenseFixeDBAdapter = new DepenseFixeDBAdapter(this);

        setWidgets();
        setListeners();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWidgets() {
        intent = getIntent();
        afficherCalendrier();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void afficherCalendrier() {
        LocalDate date = LocalDate.now();
        listDepensesFixes = depenseFixeDBAdapter.findDepensesFixesParMois(date);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCalendar);
        CalendrierRVAdapter calendrierRVAdapter = new CalendrierRVAdapter(this, listDepensesFixes);
        recyclerView.setAdapter(calendrierRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void setListeners() {
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changerEtatPaye(int adapterPosition) {
    }
}