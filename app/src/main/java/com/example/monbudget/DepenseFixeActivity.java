package com.example.monbudget;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import AccessPersistence.CompteDBAdapter;
import AccessPersistence.DepenseFixeDBAdapter;
import model.Compte;
import model.DepenseFixe;
import model.Revenue;

public class DepenseFixeActivity extends AppCompatActivity {
    private FloatingActionButton fabAjouter;
    private List<Compte> listComptes;
    private List<DepenseFixe> listDepenseFixe;
    private CompteDBAdapter compteDBAdapter;
    private DepenseFixeDBAdapter depenseFixeDBAdapter;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private LocalDate dateChoisi;
    private Context context;
    private String[] frequences = {"Paiement unique", "A tous les semaines", "Au deux semaines", "A tous les mois"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_fixe);
        //Ajout du back button dans la bar de navigation
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.compteDBAdapter = new CompteDBAdapter(DepenseFixeActivity.this);
        this.depenseFixeDBAdapter = new DepenseFixeDBAdapter(DepenseFixeActivity.this);
        listComptes = compteDBAdapter.findAllComptes();
        setWidgets();
        setListeners();
        context = this;
        afficherDepensesFixe();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void afficherDepensesFixe() {
        listDepenseFixe = depenseFixeDBAdapter.findAllDepensesFixes();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewFixe);
        DepenseFixeRVAdapter adapter = new DepenseFixeRVAdapter(this, listDepenseFixe);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListeners() {
        fabAjouter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                openDialogueAjouterDepense();
            }
        });
    }

    private void setWidgets() {
        fabAjouter = findViewById(R.id.fabDepenseFixe);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void openDialogueAjouterDepense() {
        LayoutInflater inflater = LayoutInflater.from(DepenseFixeActivity.this);
        View subView = inflater.inflate(R.layout.depense_fixe_dialogue, null);
        EditText txtDescription = (EditText) subView.findViewById(R.id.txtDescriptionFixe);
        EditText txtMontant = (EditText) subView.findViewById(R.id.txtMontantFixe);
        TextView txtDate = (TextView) subView.findViewById(R.id.datePickerFixe);

        //Ajout du datePicker
        dateChoisi = LocalDate.now();
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DepenseFixeActivity.this,
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

        //Spinner dropdown categorie
        Spinner spinnerCategorie = (Spinner) subView.findViewById(R.id.spinnerCategorieFixe);
        ArrayAdapter adapterCategorie = new ArrayAdapter(this, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.CATEGORIES_FIXES);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorie.setAdapter(adapterCategorie);


        //Spinner dropdown sousCategorie
        Spinner spinnerSousCategorie = (Spinner) subView.findViewById(R.id.spinnerSousCategorieFixe);
        ArrayAdapter adapterSousCategorieLogement = new ArrayAdapter(this, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.S_CATEGOORIE_LOGEMENT);
        adapterSousCategorieLogement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSousCategorie.setAdapter(adapterSousCategorieLogement);

        spinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String categorie = (String) adapterCategorie.getItem(position);
                setSpinnerSousCategorie(categorie, spinnerSousCategorie, adapterSousCategorieLogement);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner dropdown frequence
        Spinner spinnerFrequence = (Spinner) subView.findViewById(R.id.spinnerFrequenceFixe);
        ArrayAdapter adapterFrequence = new ArrayAdapter(this, android.R.layout.simple_spinner_item, frequences);
        adapterFrequence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequence.setAdapter(adapterFrequence);

        //Spinner dropdown compte
        Spinner spinnerComptes = (Spinner) subView.findViewById(R.id.spinnerCompteFixe);
        ArrayAdapter adapterCompte = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listComptes);
        adapterCompte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComptes.setAdapter(adapterCompte);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une depense");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();
        //Ajouter les btns de l'alerte
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //Creer un objet DepensesFixe
                    DepenseFixe depenseFixe = new DepenseFixe();
                    depenseFixe.setDescription(txtDescription.getText().toString());
                    depenseFixe.setMontant(Double.parseDouble(txtMontant.getText().toString()));
                    depenseFixe.setCategorie(IDepenseFixeConstantes.CATEGORIES_FIXES[spinnerCategorie.getSelectedItemPosition()]);
                    depenseFixe.setSousCategorie(String.valueOf(spinnerSousCategorie.getSelectedItem()));
                    depenseFixe.setDate(dateChoisi);
                    depenseFixe.setIdCompte(listComptes.get(spinnerComptes.getSelectedItemPosition()).getIdCompte());
                    int frequence = getValueOfSpinnerFrequence(spinnerFrequence.getSelectedItemPosition());
                    depenseFixe.setFrequence(frequence);
                    depenseFixeDBAdapter.ajouterDepenseFixe(depenseFixe);
                    depenseFixeDBAdapter.close();
                    afficherDepensesFixe();
                    updateSoldeCompte(depenseFixe);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DepenseFixeActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openDialogueUpdateDepense(int position) {
        LayoutInflater inflater = LayoutInflater.from(DepenseFixeActivity.this);
        View subView = inflater.inflate(R.layout.depense_fixe_dialogue, null);
        EditText txtDescription = (EditText) subView.findViewById(R.id.txtDescriptionFixe);
        EditText txtMontant = (EditText) subView.findViewById(R.id.txtMontantFixe);
        TextView txtDate = (TextView) subView.findViewById(R.id.datePickerFixe);

        //Set les valeur
        txtDescription.setText(listDepenseFixe.get(position).getDescription());
        double montant = listDepenseFixe.get(position).getMontant();
        txtMontant.setText(String.valueOf(montant));
        txtDate.setText(String.valueOf(listDepenseFixe.get(position).getDate()));

        //Ajout du datePicker
        dateChoisi = LocalDate.now();
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DepenseFixeActivity.this,
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

        //Spinner dropdown categorie
        Spinner spinnerCategorie = (Spinner) subView.findViewById(R.id.spinnerCategorieFixe);
        ArrayAdapter adapterCategorie = new ArrayAdapter(this, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.CATEGORIES_FIXES);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorie.setAdapter(adapterCategorie);
        String selectionCategorie = listDepenseFixe.get(position).getCategorie();
        int spinPosCategorie = adapterCategorie.getPosition(selectionCategorie);
        spinnerCategorie.setSelection(spinPosCategorie);


        //Spinner dropdown sousCategorie
        Spinner spinnerSousCategorie = (Spinner) subView.findViewById(R.id.spinnerSousCategorieFixe);
        ArrayAdapter adapterSousCategorieLogement = new ArrayAdapter(this, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.S_CATEGOORIE_LOGEMENT);
        adapterSousCategorieLogement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSousCategorie.setAdapter(adapterSousCategorieLogement);

        spinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String categorie = (String) adapterCategorie.getItem(position);
                setSpinnerSousCategorie(categorie, spinnerSousCategorie, adapterSousCategorieLogement);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner dropdown frequence
        Spinner spinnerFrequence = (Spinner) subView.findViewById(R.id.spinnerFrequenceFixe);
        ArrayAdapter adapterFrequence = new ArrayAdapter(this, android.R.layout.simple_spinner_item, frequences);
        adapterFrequence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequence.setAdapter(adapterFrequence);
        int positionInitSpinnerFrequence = getPreviousSpinnerFrequencePosition(listDepenseFixe.get(position).getFrequence());
        spinnerFrequence.setSelection(positionInitSpinnerFrequence);

        //Spinner dropdown compte
        Spinner spinnerComptes = (Spinner) subView.findViewById(R.id.spinnerCompteFixe);
        ArrayAdapter adapterCompte = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listComptes);
        adapterCompte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComptes.setAdapter(adapterCompte);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une depense");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();
        //Ajouter les btns de l'alerte
        builder.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //Creer un objet DepensesFixe
                    DepenseFixe depenseFixe = new DepenseFixe();
                    depenseFixe.setIdDepenseFixe(listDepenseFixe.get(position).getIdDepenseFixe());
                    depenseFixe.setDescription(txtDescription.getText().toString());
                    depenseFixe.setMontant(Double.parseDouble(txtMontant.getText().toString()));
                    depenseFixe.setCategorie(IDepenseFixeConstantes.CATEGORIES_FIXES[spinnerCategorie.getSelectedItemPosition()]);
                    depenseFixe.setSousCategorie(String.valueOf(spinnerSousCategorie.getSelectedItem()));
                    depenseFixe.setDate(dateChoisi);
                    depenseFixe.setIdCompte(listComptes.get(spinnerComptes.getSelectedItemPosition()).getIdCompte());
                    int frequence = getValueOfSpinnerFrequence(spinnerFrequence.getSelectedItemPosition());
                    depenseFixe.setFrequence(frequence);
                    depenseFixeDBAdapter.updateDepenseFixe(depenseFixe);
                    depenseFixeDBAdapter.close();
                    afficherDepensesFixe();
                    mettreAJourSoldeCompte(depenseFixe, montant);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DepenseFixeActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openDialogueDeleteDepense(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DepenseFixeActivity.this);
        builder.setTitle("Alert!");
        builder.setMessage("Voulez-vous vraiment supprimer la depense " + listDepenseFixe.get(position).getDescription() + "?");
        builder.setCancelable(false);
        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            DepenseFixe depenseFixe = depenseFixeDBAdapter.trouverDepenseFixeParId(listDepenseFixe.get(position).getIdDepenseFixe());
            Toast.makeText(DepenseFixeActivity.this, "Revenu supprimer", Toast.LENGTH_LONG).show();
            enleverSoldeCompte(depenseFixe);
            depenseFixeDBAdapter.deleteDepenseFixe(depenseFixe);
            depenseFixeDBAdapter.close();
            afficherDepensesFixe();
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            Toast.makeText(DepenseFixeActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void enleverSoldeCompte(DepenseFixe depenseFixe) {
        //Faire un update du solde du compte avec le montant du revenue
        Compte compte = compteDBAdapter.trouverCompteParId(depenseFixe.getIdCompte());
        double ancienSolde = compte.getSolde();
        double montant = depenseFixe.getMontant();
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

    private void mettreAJourSoldeCompte(DepenseFixe depenseFixe, Double montant) {
        //Faire un update du solde du compte avec le montant du revenue
        Compte compte = compteDBAdapter.trouverCompteParId(depenseFixe.getIdCompte());
        double ancienSolde = compte.getSolde();
        double nouveauMontant = depenseFixe.getMontant();
        String typeCompte = compte.getType();
        double nouveauSolde;
        if(typeCompte.equals("Positif")) {
            nouveauSolde = ancienSolde + montant - nouveauMontant;
        }else{
            nouveauSolde = ancienSolde - montant + nouveauMontant;
        }
        compte.setSolde(nouveauSolde);
        compteDBAdapter.updateCompte(compte);
        compteDBAdapter.close();
    }

    private void setSpinnerSousCategorie(String categorie, Spinner spinnerSousCategorie, ArrayAdapter adapterSousCategorieLogement) {
        switch (categorie){
            case IDepenseFixeConstantes.LOGEMENT:
                spinnerSousCategorie.setAdapter(adapterSousCategorieLogement);
                break;
            case IDepenseFixeConstantes.SERVICE_PUBLIC:
                ArrayAdapter adapterServicesPublics = new ArrayAdapter(context, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.S_CATEGORIE_SERVICES_PUBLICS);
                spinnerSousCategorie.setAdapter(adapterServicesPublics);
                break;
            case IDepenseFixeConstantes.ASSURANCES:
                ArrayAdapter adapterAssurance = new ArrayAdapter(context, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.S_CATEGORIE_ASSURANCES);
                spinnerSousCategorie.setAdapter(adapterAssurance);
                break;
            case IDepenseFixeConstantes.EMPRUNTS:
                ArrayAdapter adapterEmprunts = new ArrayAdapter(context, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.S_CATEGORIE_EMPRUNTS);
                spinnerSousCategorie.setAdapter(adapterEmprunts);
                break;
            case IDepenseFixeConstantes.GARDERIE:
                ArrayAdapter adapterGarderie = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.GARDERIE));
                spinnerSousCategorie.setAdapter(adapterGarderie);
                break;
            case IDepenseFixeConstantes.FRAIS_COMPTE_BANCAIRE:
                ArrayAdapter adapterFraisBanque = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.FRAIS_COMPTE_BANCAIRE));
                spinnerSousCategorie.setAdapter(adapterFraisBanque);
                break;
            case IDepenseFixeConstantes.AUTRE:
                ArrayAdapter adapterAutre = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.AUTRE));
                spinnerSousCategorie.setAdapter(adapterAutre);
                break;
        }
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

    private int getPreviousSpinnerFrequencePosition(int frequence) {
        switch(frequence){
            case 4:
                return 3;
            default:
                return frequence;
        }
    }

    private void updateSoldeCompte(DepenseFixe depenseFixe) {
        //Faire un update du solde du compte avec le montant du revenue
        Compte compte = compteDBAdapter.trouverCompteParId(depenseFixe.getIdCompte());
        double ancienSolde = compte.getSolde();
        double montant = depenseFixe.getMontant();
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