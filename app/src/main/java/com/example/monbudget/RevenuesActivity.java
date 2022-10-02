package com.example.monbudget;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monbudget.fragments.FixeFragment;
import com.example.monbudget.fragments.VariableFregment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import AccessPersistence.CompteDBAdapter;
import AccessPersistence.RevenueDBAdapter;
import model.Compte;
import model.Revenue;

//TODO le menu des compte affiche en toString() dans le dialog box
//TODO : Est-ce possible de changer le input de la fréquence pour un dropdown menu?
//TODO : ou ajouter un checkbox qui le disable si on veut un versement unique et qui set la valeur à 0
//TODO : Important pour savoir comment je traite les versements récurrents ou à une date unique.

public class RevenuesActivity extends AppCompatActivity {
    private CompteDBAdapter compteDBAdapter;
    private RevenueDBAdapter revenueDBAdapter;
    private List<Compte> listComptes;
    private List<Revenue> listRevenues;
    private Intent intent;
    private String[] types = {"Salaire net", "Revenue irregulier", "Placement", "Pension alimentaire", "Rentes", "Autre revenus"};
    private String[] frequences = {"Versement unique", "A tous les semaines", "Au deux semaines", "A tous les mois"};
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private LocalDate dateChoisi;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenues);
        this.compteDBAdapter = new CompteDBAdapter(RevenuesActivity.this);
        this.revenueDBAdapter = new RevenueDBAdapter(RevenuesActivity.this);
        listComptes = compteDBAdapter.findAllComptes();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setWidget();
        afficherRevenues(revenueDBAdapter);
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
    private void afficherRevenues(RevenueDBAdapter revenueDBAdapter) {
        listRevenues = revenueDBAdapter.findAll();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewRevenue);
        RevenueRVAdapter adapter = new RevenueRVAdapter(this, listRevenues);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setWidget() {
        intent = getIntent();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onAjouterRevenue(View view) {
        openDialogueAjouterRevenue();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void openDialogueAjouterRevenue() {
        LayoutInflater inflater = LayoutInflater.from(RevenuesActivity.this);
        View subView = inflater.inflate(R.layout.revenue_dialogue, null);
        EditText txtDescription = (EditText) subView.findViewById(R.id.txtDescriptionRevenue);
        EditText txtMontant = (EditText) subView.findViewById(R.id.txtMontantRevenue);
        TextView txtDate = (TextView) subView.findViewById(R.id.lblDatePickerRevenue);

        //Ajout du datepicker
        dateChoisi = LocalDate.now();
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RevenuesActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date;
                String monthString;
                String dayString;
                if(month < 10){
                   monthString = "0" + month;
                }else{
                    monthString = String.valueOf(month);
                }
                if(day < 10){
                    dayString = "0" + day;
                }else{
                    dayString = String.valueOf(day);
                }
                date = dayString + "/" + monthString + "/" + year;
                dateChoisi = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                txtDate.setText(date);
            }
        };

        //Spinner dropdown types
        Spinner spinnerType = (Spinner) subView.findViewById(R.id.spinnerTypeRevenue);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(aa);

        //Spinner dropdown compte
        Spinner spinnerCompte = (Spinner) subView.findViewById(R.id.spinnerRevenueCompte);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listComptes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompte.setAdapter(arrayAdapter);

        //Spinner dropdown frequence
        Spinner spinnerFrequence = (Spinner) subView.findViewById(R.id.spinnerFequRevenue);
        ArrayAdapter adapterArray = new ArrayAdapter(this, android.R.layout.simple_spinner_item, frequences);
        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequence.setAdapter(adapterArray);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter un revenue");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();
        //Ajouter les btns de l'alerte
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //Creer un objet revenues
                    Revenue revenue = new Revenue();
                    revenue.setDescription(txtDescription.getText().toString());
                    revenue.setMontant(Double.parseDouble(txtMontant.getText().toString()));
                    int positionSpinnerFrequence = spinnerFrequence.getSelectedItemPosition();
                    int frequence = getValueOfSpinnerFrequence(positionSpinnerFrequence);
                    revenue.setFrequence(frequence);
                    revenue.setDate(dateChoisi);
                    int positionSpinnerType = spinnerType.getSelectedItemPosition();
                    revenue.setType(types[positionSpinnerType]);
                    int positionSpinnerCompte = spinnerCompte.getSelectedItemPosition();
                    revenue.setIdCompte(listComptes.get(positionSpinnerCompte).getIdCompte());
                    revenueDBAdapter.ajouterRevenue(revenue);
                    revenueDBAdapter.close();
                    afficherRevenues(revenueDBAdapter);

                    updateSoldeCompte(revenue);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(RevenuesActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();

    }

    private int getValueOfSpinnerFrequence(int positionSpinnerFrequence) {
        switch (positionSpinnerFrequence){
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            default:
                return 0;
        }
    }

    private void updateSoldeCompte(Revenue revenue) {
        //Faire un update du solde du compte avec le montant du revenue
        Compte compte = compteDBAdapter.trouverCompteParId(revenue.getIdCompte());
        double ancienSolde = compte.getSolde();
        double montant = revenue.getMontant();
        String typeCompte = compte.getType();
        double nouveauSolde;
        if(typeCompte.equals("Positif")) {
            nouveauSolde = ancienSolde + montant;
        }else{
            nouveauSolde = ancienSolde - montant;
        }
        compte.setSolde(nouveauSolde);
        compteDBAdapter.updateCompte(compte);
        compteDBAdapter.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openDialogueUpdateRevenue(int position) {
        LayoutInflater inflater = LayoutInflater.from(RevenuesActivity.this);
        View subView = inflater.inflate(R.layout.revenue_dialogue, null);
        EditText txtDescription = (EditText) subView.findViewById(R.id.txtDescriptionRevenue);
        EditText txtMontant = (EditText) subView.findViewById(R.id.txtMontantRevenue);
        TextView txtDate = (TextView) subView.findViewById(R.id.lblDatePickerRevenue);

        //Set les valeurs
        txtDescription.setText(listRevenues.get(position).getDescription());
        txtMontant.setText(String.valueOf(listRevenues.get(position).getMontant()));
        double montant = listRevenues.get(position).getMontant();
        txtDate.setText(String.valueOf(listRevenues.get(position).getDate()));

        //Ajout du datepicker
        dateChoisi = LocalDate.now();
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RevenuesActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date;
                String monthString;
                String dayString;
                if(month < 10){
                    monthString = "0" + month;
                }else{
                    monthString = String.valueOf(month);
                }
                if(day < 10){
                    dayString = "0" + day;
                }else{
                    dayString = String.valueOf(day);
                }
                date = dayString + "/" + monthString + "/" + year;
                dateChoisi = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                txtDate.setText(date);
            }
        };

        //Spinner dropdown types
        Spinner spinnerType = (Spinner) subView.findViewById(R.id.spinnerTypeRevenue);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(aa);
        String selectionType = listRevenues.get(position).getType();
        int spinnerPosType = aa.getPosition(selectionType);
        spinnerType.setSelection(spinnerPosType);

        //Spinner dropdown compte
        Spinner spinnerCompte = (Spinner) subView.findViewById(R.id.spinnerRevenueCompte);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listComptes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompte.setAdapter(arrayAdapter);

        //Spinner dropdown frequence
        Spinner spinnerFrequence = (Spinner) subView.findViewById(R.id.spinnerFequRevenue);
        ArrayAdapter adapterArray = new ArrayAdapter(this, android.R.layout.simple_spinner_item, frequences);
        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequence.setAdapter(adapterArray);
        int positionInitSpinnerFrequence = getPreviousSpinnerPosition(listRevenues.get(position).getFrequence());
        spinnerFrequence.setSelection(positionInitSpinnerFrequence);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modifier un revenue");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();
        //Ajouter les btns de l'alerte
        builder.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //Creer un objet revenues
                    Revenue revenue = new Revenue();
                    revenue.setIdRevenue(listRevenues.get(position).getIdRevenue());
                    revenue.setDescription(txtDescription.getText().toString());
                    revenue.setMontant(Double.parseDouble(txtMontant.getText().toString()));
                    int positionSpinnerFrequence = spinnerFrequence.getSelectedItemPosition();
                    int frequence = getValueOfSpinnerFrequence(positionSpinnerFrequence);
                    revenue.setFrequence(frequence);
                    revenue.setDate(dateChoisi);
                    int positionSpinnerType = spinnerType.getSelectedItemPosition();
                    revenue.setType(types[positionSpinnerType]);
                    int positionSpinnerCompte = spinnerCompte.getSelectedItemPosition();
                    revenue.setIdCompte(listComptes.get(positionSpinnerCompte).getIdCompte());
                    revenueDBAdapter.updateRevenue(revenue);
                    afficherRevenues(revenueDBAdapter);

                    mettreAJourSoldeCompte(revenue, montant);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(RevenuesActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    private int getPreviousSpinnerPosition(int frequence) {
        switch(frequence){
            case 4:
                return 3;
            default:
                return frequence;
        }
    }

    private void mettreAJourSoldeCompte(Revenue revenue, Double montant) {
        //Faire un update du solde du compte avec le montant du revenue
        Compte compte = compteDBAdapter.trouverCompteParId(revenue.getIdCompte());
        double ancienSolde = compte.getSolde();
        double nouveauMontant = revenue.getMontant();
        String typeCompte = compte.getType();
        double nouveauSolde;
        if(typeCompte.equals("Positif")) {
            nouveauSolde = ancienSolde - montant + nouveauMontant;
        }else{
            nouveauSolde = ancienSolde + montant - nouveauMontant;
        }
        compte.setSolde(nouveauSolde);
        compteDBAdapter.updateCompte(compte);
        compteDBAdapter.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openDialogueDeleteRevenue(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RevenuesActivity.this);
        builder.setTitle("Alert!");
        builder.setMessage("Voulez-vous vraiment supprimer le revenue " + listRevenues.get(position).getDescription() + "?");
        builder.setCancelable(false);
        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            Revenue revenue = revenueDBAdapter.trouverRevenueParId(listRevenues.get(position).getIdRevenue());
            Toast.makeText(RevenuesActivity.this, "Revenu supprimer", Toast.LENGTH_LONG).show();
            enleverSoldeCompte(revenue);
            revenueDBAdapter.deleteRevenue(revenue);
            afficherRevenues(revenueDBAdapter);
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            Toast.makeText(RevenuesActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void enleverSoldeCompte(Revenue revenue) {
        //Faire un update du solde du compte avec le montant du revenue
        Compte compte = compteDBAdapter.trouverCompteParId(revenue.getIdCompte());
        double ancienSolde = compte.getSolde();
        double montant = revenue.getMontant();
        String typeCompte = compte.getType();
        double nouveauSolde;
        if(typeCompte.equals("Positif")) {
            nouveauSolde = ancienSolde - montant;
        }else{
            nouveauSolde = ancienSolde + montant;
        }
        compte.setSolde(nouveauSolde);
        compteDBAdapter.updateCompte(compte);
        compteDBAdapter.close();
    }
}