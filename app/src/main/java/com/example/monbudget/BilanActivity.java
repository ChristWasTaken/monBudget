package com.example.monbudget;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
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

    //TODO : (Must Have)TAB pour afficher le mois courrant(défaut) ou un des 2 mois précédant

    //TODO : (Should Have) méthode pour extrapoler les revenus recursif au bonne date du mois selectionné
    //TODO : en ce basé sur la date de création du premier versement enregistré.

    //TODO : (Could have) TAB pour choisir d'autre données pour le piechart (revenus, dépenses, etc...)

    private DepenseFixeDBAdapter depenseFixeDBAdapter;
    private DepenseVariableDBAdapter depenseVariableDBAdapter;
    private RevenueDBAdapter revenueDBAdapter;

    private ProgressBar progressBarRevenue;
    private ProgressBar progressBarDepense;

    private TextView textViewRevenue;
    private TextView textViewDepense;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilan);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
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
        this.depenseFixeDBAdapter = new DepenseFixeDBAdapter(BilanActivity.this);
        this.depenseVariableDBAdapter = new DepenseVariableDBAdapter(BilanActivity.this);
        this.revenueDBAdapter = new RevenueDBAdapter(BilanActivity.this);

        this.progressBarRevenue = findViewById(R.id.progressBarRevenues);
        this.progressBarDepense = findViewById(R.id.progressBarDepenses);
        this.textViewRevenue = findViewById(R.id.txtRevenue);
        this.textViewDepense = findViewById(R.id.txtDepenses);

        onAfficher();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onAfficher() {

        // Chargement des données du bilan
        List<DepenseFixe> depensesMensuelles = chargerListeDepenses();
        double revenueTotal = trouverRevenueTotal();

        // Chargement des données dans les progress bar
        this.progressBarRevenue.setProgress((int) revenueTotal);
        textViewRevenue.setText(new StringBuilder().append("Revenues mensuels totaux: ").append(String.valueOf(revenueTotal)).append(" $").toString());

        double depenseTotal = trouverDepenseTotal(depensesMensuelles);
        double pourcentageDepense = (depenseTotal / revenueTotal) * 100;
        this.progressBarDepense.setProgress((int) pourcentageDepense);
        textViewDepense.setText(new StringBuilder().append("Dépenses mensuelles totales: ").append(String.valueOf(depenseTotal)).append(" $").toString());

        // Création du piechart
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setCenterText("Dépenses mensuelles");
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextColor(R.color.dark_grey);
        pieChart.setHoleRadius(50f);

        // Création des données du piechart
        ArrayList<PieEntry> depenses = new ArrayList<>();

        for (DepenseFixe depenseFixe : depensesMensuelles) {
            depenses.add(new PieEntry((float) depenseFixe.getMontant() , depenseFixe.getCategorie()));
        }

        // Création du dataset + configuration du formatage des valeurs
        PieDataSet pieDataSet = new PieDataSet(depenses, "");
        pieDataSet.setValueFormatter(new MyValueFormatter());

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

        // Création de la légende + configuration
        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setTextSize(18f);
        legend.setTextColor(R.color.dark_grey);
        legend.setFormSize(18f);
        legend.setXEntrySpace(50f);
        legend.setFormToTextSpace(10f);


        pieChart.animate();
    }

    private double trouverDepenseTotal(List<DepenseFixe> depensesMensuelles) {
        double depenseTotal = 0;
        for (DepenseFixe depenseFixe : depensesMensuelles) {
            depenseTotal += depenseFixe.getMontant();
        }
        return depenseTotal;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private double trouverRevenueTotal() {
        double revenuesTotal = 0;
        List<Revenue> revenues = this.revenueDBAdapter.findAllRevenueByMonth(LocalDate.now().getMonthValue());
        for (Revenue revenue : revenues) {
            revenuesTotal += revenue.getMontant();
        }
        return revenuesTotal;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<DepenseFixe> chargerListeDepenses() {
        // Charger toutes les dépenses mensuels de la BD
        List<DepenseFixe> depensesFixes = depenseFixeDBAdapter.findDepensesFixesParMois(LocalDate.now().getMonthValue());
        List<DepenseVariable> depensesVariables = depenseVariableDBAdapter.findDepenseVariableByMonth(LocalDate.now().getMonthValue());

        // Tableau des catégories de dépenses
        final String[] CATEGORIES = {"Habitation", "Services publics", "Assurance",
                "Emprunts", "Dépenses variables", "Autres"};

        // Depense fixe pour stocker toutes les dépenses mensuelles variables totalisées
        DepenseFixe depensesVariablesMensuelles = new DepenseFixe("Dépenses variables", 0,"Dépenses variables",
                "", 0, LocalDate.now(), 0);
        for (DepenseVariable depenseVariable : depensesVariables) {
            depensesVariablesMensuelles.setMontant(depensesVariablesMensuelles.getMontant() + depenseVariable.getMontant());
        }
        depensesFixes.add(depensesVariablesMensuelles);

        // Créer une liste des dépenses mensuelles filtrer par catégories de dépenses
        List<DepenseFixe> depensesMensuelles = new ArrayList<>();
        for (String categorie : CATEGORIES) {
            DepenseFixe depenseMensuelle = new DepenseFixe(categorie, 0, categorie, "", 0, LocalDate.now(), 0);
            for (DepenseFixe depenseFixe : depensesFixes) {
                if (depenseFixe.getCategorie().equals(categorie)) {
                    depenseMensuelle.setMontant(depenseMensuelle.getMontant() + depenseFixe.getMontant());
                }
            }
            if(depenseMensuelle.getMontant() != 0){
                depensesMensuelles.add(depenseMensuelle);
            }
        }
        return depensesMensuelles;
    }

    public class MyValueFormatter extends ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.00"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value) + " $"; // e.g. append a dollar-sign
        }
    }
}