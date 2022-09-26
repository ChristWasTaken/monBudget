package com.example.monbudget;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import AccessPersistence.CompteDBAdapter;
import model.Compte;

public class Comptes extends AppCompatActivity {
    private LinearLayout linearLayoutCompte;
    private CompteDBAdapter compteDBAdapter;
    private List<Compte> listComptes;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptes);
        setWidget();
        this.compteDBAdapter = new CompteDBAdapter(Comptes.this);
    }

    private void setWidget() {
        intent = getIntent();
        linearLayoutCompte = findViewById(R.id.linearLayoutCompte);
        //Besoin du try catch si la bd est vide pour eviter le null pointer exception(crash de l'app)
        try {
            afficherComptes(compteDBAdapter);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
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
        EditText txtType = (EditText) subView.findViewById(R.id.txtType);
        EditText txtInstitution = (EditText) subView.findViewById(R.id.txtInstitution);
        EditText txtNumCompte = (EditText) subView.findViewById(R.id.txtNumCompte);
        EditText txtNumSuccursale = (EditText) subView.findViewById(R.id.txtNumSuccursale);

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
                    compte.setType(txtType.getText().toString());
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