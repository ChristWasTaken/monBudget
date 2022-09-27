package com.example.monbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import AccessPersistence.CompteDBAdapter;
import model.Compte;

public class Comptes extends AppCompatActivity{
    private LinearLayout linearLayoutCompte;
    private CompteDBAdapter compteDBAdapter;
    private List<Compte> listComptes;
    private Intent intent;
    private String[] types = {"Positif", "Negatif"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptes);
        setWidget();
        this.compteDBAdapter = new CompteDBAdapter(Comptes.this);
        //Ajout du back button dans la bar de navigation
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //Permet le back button sur la bar de navigation
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setWidget() {
        intent = getIntent();
        //Besoin du try catch si la bd est vide pour eviter le null pointer exception(crash de l'app)
        //todo ne marche pas soit trouver moyen pour arrenger ou ajouter bouton pour faire afficher

    }

    private void afficherComptes(CompteDBAdapter compteDBAdapter) {
        listComptes = compteDBAdapter.findAllComptes();
        linearLayoutCompte.removeAllViews();
        for(Compte c : listComptes) {
            TextView textView = new TextView(this);
            textView.setText(c.toString());
            linearLayoutCompte.addView(textView);
        }
    }

    public void onAjouterCompte(View view) {
        openDialogueAjoutCompte();
    }

    private void openDialogueAjoutCompte() {
        //Preparer une vue
        LayoutInflater inflater = LayoutInflater.from(Comptes.this);
        View subView = inflater.inflate(R.layout.dialogue_comptes_ajout, null);
        EditText txtDescription = (EditText) subView.findViewById(R.id.txtDescription);
        EditText txtSolde = (EditText) subView.findViewById(R.id.txtSolde);
        EditText txtInstitution = (EditText) subView.findViewById(R.id.txtInstitution);
        EditText txtNumCompte = (EditText) subView.findViewById(R.id.txtNumCompte);
        EditText txtNumSuccursale = (EditText) subView.findViewById(R.id.txtNumSuccursale);

        //Spinner pour dropdown
        Spinner spinnerCompte = (Spinner) subView.findViewById(R.id.spinnerTypeCompte);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompte.setAdapter(arrayAdapter);

        //Construire l'alerte avec la vue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter un compte");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();
        //Ajouter les btns de l'alerte
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //Creer un objet compte
                    Compte compte = new Compte();
                    compte.setDescription(txtDescription.getText().toString());
                    compte.setSolde(Double.parseDouble(txtSolde.getText().toString()));
                    int positionSpinner = spinnerCompte.getSelectedItemPosition();
                    compte.setType(types[positionSpinner]);
                    compte.setInstitution(txtInstitution.getText().toString());
                    compte.setNumCompte(Integer.parseInt(txtNumCompte.getText().toString()));
                    compte.setNumSuccursale(Integer.parseInt(txtNumSuccursale.getText().toString()));
                    //Persister l'objet
                    compteDBAdapter.ajouterCompte(compte);
                    compteDBAdapter.close();
                    afficherComptes(compteDBAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Comptes.this, "Annuler", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

}