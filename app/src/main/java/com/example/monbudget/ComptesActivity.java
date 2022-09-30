package com.example.monbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import AccessPersistence.CompteDBAdapter;
import model.Compte;

public class ComptesActivity extends AppCompatActivity{
    private LinearLayout linearLayoutCompte;
    private CompteDBAdapter compteDBAdapter;
    private List<Compte> listComptes;
    private Intent intent;
    private String[] types = {"Positif", "Negatif"};
    private TextView lblSoldeTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptes);
        setWidget();
        this.compteDBAdapter = new CompteDBAdapter(ComptesActivity.this);
        //Ajout du back button dans la bar de navigation
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Appel de la methode pour remplir la liste
        afficherComptes(compteDBAdapter);
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
        lblSoldeTotal = findViewById(R.id.lblSoldeTotal);
    }

    private void afficherComptes(CompteDBAdapter compteDBAdapter) {
        listComptes = compteDBAdapter.findAllComptes();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCompte);
        ComptesRVAdapter adapter = new ComptesRVAdapter(this, listComptes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lblSoldeTotal.setText(String.valueOf(calculerTotalComptes()) + "$");
    }

    private double calculerTotalComptes(){
        double positif = 0;
        double negatif = 0;
        for(Compte compte : listComptes){
            String type = compte.getType();
            if(type.equals("Positif")){
                positif += compte.getSolde();
            }else{
                negatif += compte.getSolde();
            }
        }
        return (positif - negatif);
    }

    public void onAjouterCompte(View view) {
        openDialogueAjoutCompte();
    }

    private void openDialogueAjoutCompte() {
        //Preparer une vue
        LayoutInflater inflater = LayoutInflater.from(ComptesActivity.this);
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
                Toast.makeText(ComptesActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    public void openDialogueUpdateCompte(int position) {
        //Preparer une vue
        LayoutInflater inflater = LayoutInflater.from(ComptesActivity.this);
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
        int positionSpinner;
        String valueSpinner = listComptes.get(position).getType();
        if(valueSpinner.equals("Positif")){
            positionSpinner = 0;
        }else{
            positionSpinner = 1;
        }

        //Set le text de chaque champs
        txtDescription.setText(listComptes.get(position).getDescription());
        txtSolde.setText(String.valueOf(listComptes.get(position).getSolde()));
        txtInstitution.setText(listComptes.get(position).getInstitution());
        txtNumCompte.setText(String.valueOf(listComptes.get(position).getNumCompte()));
        txtNumSuccursale.setText(String.valueOf(listComptes.get(position).getNumSuccursale()));
        spinnerCompte.setSelection(positionSpinner);

        //Construire l'alerte avec la vue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modifier un compte");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();
        //Ajouter les btns de l'alerte
        builder.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //Creer un objet compte
                    Compte compte = new Compte();
                    compte.setIdCompte(listComptes.get(position).getIdCompte());
                    compte.setDescription(txtDescription.getText().toString());
                    compte.setSolde(Double.parseDouble(txtSolde.getText().toString()));
                    int positionSpinner = spinnerCompte.getSelectedItemPosition();
                    compte.setType(types[positionSpinner]);
                    compte.setInstitution(txtInstitution.getText().toString());
                    compte.setNumCompte(Integer.parseInt(txtNumCompte.getText().toString()));
                    compte.setNumSuccursale(Integer.parseInt(txtNumSuccursale.getText().toString()));
                    //Updater l'objet
                    compteDBAdapter.updateCompte(compte);
                    compteDBAdapter.close();
                    afficherComptes(compteDBAdapter);
                    //Afficher un toast success!
                    Toast.makeText(ComptesActivity.this, "Modification Reussite", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ComptesActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    public void openDialogueDeleteCompte(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ComptesActivity.this);
        builder.setTitle("Alert!");
        builder.setMessage("Voulez-vous vraiment supprimer le compte " + listComptes.get(position).getDescription() + "?");
        builder.setCancelable(false);
        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            Compte compte = compteDBAdapter.trouverCompteParId(listComptes.get(position).getIdCompte());
            compteDBAdapter.deleteCompte(compte);
            compteDBAdapter.close();
            Toast.makeText(ComptesActivity.this, "Compte supprimer", Toast.LENGTH_LONG).show();
            afficherComptes(compteDBAdapter);
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            Toast.makeText(ComptesActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}