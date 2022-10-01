package com.example.monbudget;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import AccessPersistence.DepenseFixeDBAdapter;
import AccessPersistence.DepenseVariableDBAdapter;
import AccessPersistence.RevenueDBAdapter;
import model.DepenseFixe;
import model.DepenseVariable;
import model.Revenue;

public class BilanActivity extends AppCompatActivity {

    private DepenseFixeDBAdapter depenseFixeDBAdapter;
    private DepenseVariableDBAdapter depenseVariableDBAdapter;
    private RevenueDBAdapter revenueDBAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilan);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.depenseFixeDBAdapter = new DepenseFixeDBAdapter(BilanActivity.this);
        this.depenseVariableDBAdapter = new DepenseVariableDBAdapter(BilanActivity.this);
        this.revenueDBAdapter = new RevenueDBAdapter(BilanActivity.this);
        setWidgets();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWidgets() {
        onAfficher();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onAfficher() {
        depenseFixeDBAdapter.open();
        depenseFixeDBAdapter.ajouterDepenseFixe(new DepenseFixe("Loyer", 500,
                "Logement", "Loyer", 0,
                LocalDate.of(2022, 1, 1), 1));

        depenseVariableDBAdapter.open();
        depenseVariableDBAdapter.ajouterDepenseVariable(new DepenseVariable("Achat",
                100, "Alimentation", "Achat",
                LocalDate.of(2021, 1, 1), 1));

        revenueDBAdapter.open();
        revenueDBAdapter.ajouterRevenue(new Revenue("Salaire", 1000.00,
                "Salaire", 4,
                LocalDate.of(2021, 1, 1), 1));

        PieChart pieChart = findViewById(R.id.pieChart);
        LocalDate date = LocalDate.of(2020, 1, 1);


        List<DepenseFixe> depenseFixesMensuelles = depenseFixeDBAdapter.findAllDepensesFixes();

        ArrayList<PieEntry> depenses = new ArrayList<>();
        for (DepenseFixe depenseFixe : depenseFixesMensuelles) {
            depenses.add(new PieEntry((float)depenseFixe.getMontant(), depenseFixe.getDescription()));
        }
        PieDataSet pieDataSet = new PieDataSet(depenses, "Dépenses fixes mensuelles");
        if(depenses.isEmpty()){
            depenses.add(new PieEntry(0, "Pas de dépenses"));
            pieChart.setCenterText("Ajouter des dépenses pour voir le graphique");
            pieChart.setCenterTextSize(20);
            pieChart.setCenterTextColor(Color.RED);
        }

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawSliceText(false);
        pieChart.animate();
    }
}