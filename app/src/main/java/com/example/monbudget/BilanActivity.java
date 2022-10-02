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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;

import AccessPersistence.DepenseFixeDBAdapter;
import AccessPersistence.DepenseVariableDBAdapter;
import AccessPersistence.RevenueDBAdapter;
import model.DepenseFixe;
import model.DepenseVariable;
import model.Revenue;

public class BilanActivity extends AppCompatActivity {

    //TODO : (Could have) TAB pour choisir d'autre données pour le piechart (revenus, dépenses, etc...)

    private DepenseFixeDBAdapter depenseFixeDBAdapter;
    private DepenseVariableDBAdapter depenseVariableDBAdapter;
    private RevenueDBAdapter revenueDBAdapter;

    private ProgressBar progressBarRevenue;
    private ProgressBar progressBarDepense;

    private TextView textViewDepensePourcentage;
    private TextView textViewDepenseRevenue;

    private TabLayout tabLayout;

    private List<Revenue> revenueListe;
    private List<DepenseFixe> depensesMensuellesListe;
    private double revenueTotal;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilan);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        setWidgets();
        setListeners();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWidgets() {
        this.depenseFixeDBAdapter = new DepenseFixeDBAdapter(BilanActivity.this);
        this.depenseVariableDBAdapter = new DepenseVariableDBAdapter(BilanActivity.this);
        this.revenueDBAdapter = new RevenueDBAdapter(BilanActivity.this);

        this.progressBarRevenue = findViewById(R.id.progressBarRevenues);
        this.progressBarDepense = findViewById(R.id.progressBarDepenses);
        this.textViewDepensePourcentage = findViewById(R.id.txtDepensePourcentage);
        this.textViewDepenseRevenue = findViewById(R.id.txtDepenseRevenue);

        this.tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setScrollPosition(2, 0f, true);

        onAfficher(2);
    }

    private void setListeners() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onAfficher(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
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
    private void onAfficher(int tabPosition) {

        // Récupérer les données du mois à afficher
        chargerDonneeDuMoisAffiche(tabPosition);

        // Chargement des données dans les progress bar
        this.progressBarRevenue.setProgress((int) revenueTotal);

        double depenseTotal = trouverDepenseTotal(depensesMensuellesListe);
        double pourcentageDepense = (depenseTotal / revenueTotal) * 100;
        this.progressBarDepense.setProgress((int) pourcentageDepense);

        Formatter formatter = new Formatter();
        formatter = formatter.format("%1$.2f/%2$.2f $", depenseTotal, revenueTotal);

        textViewDepensePourcentage.setText(new StringBuilder().append((int)pourcentageDepense).append("/").append(100));
        textViewDepenseRevenue.setText(formatter.toString());

        // Création du piechart
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.clear();
        pieChart.setCenterText("Dépenses mensuelles");
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextColor(R.color.dark_grey);
        pieChart.setHoleRadius(50f);

        // Création des données du piechart
        ArrayList<PieEntry> depenses = new ArrayList<>();

        for (DepenseFixe depenseFixe : depensesMensuellesListe) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void chargerDonneeDuMoisAffiche(int tabPosition) {
        // Récupérer les données du mois à afficher
        LocalDate dateCourante = LocalDate.now();

        Objects.requireNonNull(tabLayout.getTabAt(2)).setText(dateCourante.getMonth().toString());
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText(dateCourante.minusMonths(1).getMonth().toString());
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText(dateCourante.minusMonths(2).getMonth().toString());
        switch (tabPosition) {
            case 0:
                dateCourante = dateCourante.minusMonths(2);
                Log.v("log", "dateCourante = " + dateCourante);
                chargerDonneesMoisCourant(dateCourante);
                break;
            case 1:
                dateCourante = dateCourante.minusMonths(1);
                Log.v("log", "dateCourante = " + dateCourante);

                chargerDonneesMoisCourant(dateCourante);
                break;
            case 2:
                Log.v("log", "dateCourante = " + dateCourante);

                chargerDonneesMoisCourant(dateCourante);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void chargerDonneesMoisCourant(LocalDate moisCourant) {
        // Chargement des données du bilan
        if(revenueTotal != 0){
            revenueTotal = 0;
        }
        if(depensesMensuellesListe != null){
            depensesMensuellesListe.clear();
        }
        Log.v("log", "moisCourant = " + moisCourant);
        revenueTotal = trouverRevenueTotal(moisCourant);
        depensesMensuellesListe = chargerListeDepenses(moisCourant);
    }

    private double trouverDepenseTotal(List<DepenseFixe> depensesMensuelles) {
        double depenseTotal = 0;
        for (DepenseFixe depenseFixe : depensesMensuelles) {
            depenseTotal += depenseFixe.getMontant();
        }
        return depenseTotal;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private double trouverRevenueTotal(LocalDate moisAfficher) {
        double revenuesTotal = 0;
        if(revenueListe != null){
            revenueListe.clear();
        }
        revenueListe = this.revenueDBAdapter.findAll();

        for (Revenue revenue : revenueListe) {
            if(revenue.getFrequence() != 0){
                List<Integer> datesPaiements = revenue.trouverJoursVersement(revenue, moisAfficher);
                revenuesTotal += (datesPaiements.size()) * revenue.getMontant();
            } else {
                if (revenue.getDate().getMonthValue() == moisAfficher.getMonthValue()) {
                    revenuesTotal += revenue.getMontant();
                }
            }
        }
        return revenuesTotal;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<DepenseFixe> chargerListeDepenses(LocalDate moisCourrant) {
        // Charger toutes les dépenses mensuels de la BD
        List<DepenseFixe> depensesFixes = depenseFixeDBAdapter.findAllDepensesFixes();
        List<DepenseVariable> depensesVariables = depenseVariableDBAdapter.findDepenseVariableByMonth(moisCourrant);

        // Tableau des catégories de dépenses
        final String[] CATEGORIES = {"Habitation", "Services publics", "Assurance",
                "Emprunts", "Dépenses variables", "Autres"};

        // Depense fixe pour stocker toutes les dépenses mensuelles variables totalisées
        DepenseFixe depensesVariablesMensuelles = new DepenseFixe("Dépenses variables", 0,"Dépenses variables",
                "", 0, moisCourrant, 0);
        for (DepenseVariable depenseVariable : depensesVariables) {
            depensesVariablesMensuelles.setMontant(depensesVariablesMensuelles.getMontant() + depenseVariable.getMontant());
        }
        depensesFixes.add(depensesVariablesMensuelles);

        // Créer une liste des dépenses mensuelles filtrer par catégories de dépenses
        List<DepenseFixe> depensesMensuelles = new ArrayList<>();
        for (String categorie : CATEGORIES) {
            DepenseFixe depenseMensuelle = new DepenseFixe(categorie, 0, categorie, "", 0, moisCourrant, 0);
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