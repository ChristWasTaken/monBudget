package com.example.monbudget;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import AccessPersistence.CompteDBAdapter;
import AccessPersistence.DepenseFixeDBAdapter;
import AccessPersistence.DepenseVariableDBAdapter;
import AccessPersistence.RevenueDBAdapter;
import Moqups.InsertsDeTest;
import model.Compte;
import model.DepenseFixe;
import model.DepenseVariable;
import model.Revenue;

//import AccessPersistence.DepenseFixeDBAdapterBU;


public class MainActivity extends AppCompatActivity {
    private Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidgets();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWidgets() {
        ajoutsStaticBidon();
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
        intent = new Intent(MainActivity.this, CalendrierActivity.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void ajoutsStaticBidon() {
        CompteDBAdapter compteDBAdapter = new CompteDBAdapter(this);
        DepenseFixeDBAdapter depenseFixeDBAdapter = new DepenseFixeDBAdapter(MainActivity.this);
        DepenseVariableDBAdapter depenseVariableDBAdapter = new DepenseVariableDBAdapter(MainActivity.this);
        RevenueDBAdapter revenueDBAdapter = new RevenueDBAdapter(MainActivity.this);

        List<Compte> listeComptes = compteDBAdapter.findAllComptes();
        if(listeComptes.isEmpty()){
            listeComptes = InsertsDeTest.getComptes();
            for (Compte compte : listeComptes) {
                compteDBAdapter.ajouterCompte(compte);
            }
        }

//        depenseFixeDBAdapter.open();
//        depenseVariableDBAdapter.open();
//        revenueDBAdapter.open();

        List<DepenseFixe> depenseFixes = depenseFixeDBAdapter.findAllDepensesFixes();
        if(depenseFixes.isEmpty()) {
            depenseFixes = InsertsDeTest.getDepensesFixes();
            for (DepenseFixe depenseFixe : depenseFixes) {
                depenseFixeDBAdapter.ajouterDepenseFixe(depenseFixe);
            }
        }

        List<DepenseVariable> depenseVariables = depenseVariableDBAdapter.findAll();
        if(depenseVariables.isEmpty()) {
            depenseVariables = InsertsDeTest.getDepensesVariables();
            for (DepenseVariable depenseVariable : depenseVariables) {
                depenseVariableDBAdapter.ajouterDepenseVariable(depenseVariable);
            }
        }

        List<Revenue> revenues = revenueDBAdapter.findAll();
        if(revenues.isEmpty()) {
            revenues = InsertsDeTest.getRevenues();
            for (Revenue revenue : revenues) {
                revenueDBAdapter.ajouterRevenue(revenue);
            }
        }
    }

}