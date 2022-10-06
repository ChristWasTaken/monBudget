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
import AccessPersistence.DepenseVariableDBAdapter;
import model.Compte;
import model.DepenseFixe;
import model.DepenseVariable;

public class DepensesVariableActivity extends AppCompatActivity {
    private FloatingActionButton fabAjouter;
    private List<Compte> listComptes;
    private List<DepenseVariable> listDepenseVariable;
    private CompteDBAdapter compteDBAdapter;
    private DepenseVariableDBAdapter depenseVariableDBAdapter;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private LocalDate dateChoisi;
    private Context context;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depenses_variable);
        //Ajout du back button dans la bar de navigation
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.compteDBAdapter = new CompteDBAdapter(DepensesVariableActivity.this);
        this.depenseVariableDBAdapter = new DepenseVariableDBAdapter(DepensesVariableActivity.this);
        listComptes = compteDBAdapter.findAllComptes();
        setwidgets();
        setListeners();
        context = this;
        afficherDepensesVariable();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void afficherDepensesVariable() {
        listDepenseVariable = depenseVariableDBAdapter.findAll();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewVariable);
        DepenseVariableRVAdapter adapter = new DepenseVariableRVAdapter(this, listDepenseVariable);
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

    private void setwidgets() {
        fabAjouter = findViewById(R.id.fabDepenseVariable);
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
        LayoutInflater inflater = LayoutInflater.from(DepensesVariableActivity.this);
        View subView = inflater.inflate(R.layout.depense_variable_dialogue, null);
        EditText txtDescription = (EditText) subView.findViewById(R.id.txtDescriptionVariable);
        EditText txtMontant = (EditText) subView.findViewById(R.id.txtMontantVariable);
        TextView txtDate = (TextView) subView.findViewById(R.id.datePickerVariable);

        //Ajout du datePicker
        dateChoisi = LocalDate.now();
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DepensesVariableActivity.this,
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
        Spinner spinnerCategorie = (Spinner) subView.findViewById(R.id.spinnerCategorieVariable);
        ArrayAdapter adapterCategorie = new ArrayAdapter(this, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.CATEGORIES_VARIABLE);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorie.setAdapter(adapterCategorie);


        //Spinner dropdown sousCategorie
        Spinner spinnerSousCategorie = (Spinner) subView.findViewById(R.id.spinnerSousCategorieVariable);
        ArrayAdapter adapterSousCategorieAlimentation = new ArrayAdapter(this, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.S_CATEGORIE_ALIMENTATION);
        adapterSousCategorieAlimentation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSousCategorie.setAdapter(adapterSousCategorieAlimentation);

        spinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String categorie = (String) adapterCategorie.getItem(position);
                setSpinnerSousCategorie(categorie, spinnerSousCategorie, adapterSousCategorieAlimentation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner dropdown compte
        Spinner spinnerComptes = (Spinner) subView.findViewById(R.id.spinnerCompteVariable);
        ArrayAdapter adapterCompte = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listComptes);
        adapterCompte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComptes.setAdapter(adapterCompte);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une depense");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();
        //Ajouter les btns de l'alerte
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //Creer un objet DepensesVariable
                    DepenseVariable depenseVariable = new DepenseVariable();
                    depenseVariable.setDescription(txtDescription.getText().toString());
                    depenseVariable.setMontant(Double.parseDouble(txtMontant.getText().toString()));
                    depenseVariable.setCategorie(IDepenseFixeConstantes.CATEGORIES_VARIABLE[spinnerCategorie.getSelectedItemPosition()]);
                    depenseVariable.setSousCategorie(String.valueOf(spinnerSousCategorie.getSelectedItem()));
                    depenseVariable.setDate(dateChoisi);
                    depenseVariable.setIdCompte(listComptes.get(spinnerComptes.getSelectedItemPosition()).getIdCompte());
                    depenseVariableDBAdapter.ajouterDepenseVariable(depenseVariable);
                    depenseVariableDBAdapter.close();
                    afficherDepensesVariable();
                    updateSoldeCompte(depenseVariable);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DepensesVariableActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openDialogueUpdateDepense(int position) {
        LayoutInflater inflater = LayoutInflater.from(DepensesVariableActivity.this);
        View subView = inflater.inflate(R.layout.depense_variable_dialogue, null);
        EditText txtDescription = (EditText) subView.findViewById(R.id.txtDescriptionVariable);
        EditText txtMontant = (EditText) subView.findViewById(R.id.txtMontantVariable);
        TextView txtDate = (TextView) subView.findViewById(R.id.datePickerVariable);

        //Set les valeur
        txtDescription.setText(listDepenseVariable.get(position).getDescription());
        double montant = listDepenseVariable.get(position).getMontant();
        txtMontant.setText(String.valueOf(montant));
        txtDate.setText(String.valueOf(listDepenseVariable.get(position).getDate()));

        //Ajout du datePicker
        dateChoisi = LocalDate.now();
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DepensesVariableActivity.this,
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
        Spinner spinnerCategorie = (Spinner) subView.findViewById(R.id.spinnerCategorieVariable);
        ArrayAdapter adapterCategorie = new ArrayAdapter(this, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.CATEGORIES_VARIABLE);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorie.setAdapter(adapterCategorie);
        String selectionCategorie = listDepenseVariable.get(position).getCategorie();
        int spinPosCategorie = adapterCategorie.getPosition(selectionCategorie);
        spinnerCategorie.setSelection(spinPosCategorie);


        //Spinner dropdown sousCategorie
        Spinner spinnerSousCategorie = (Spinner) subView.findViewById(R.id.spinnerSousCategorieVariable);
        ArrayAdapter adapterSousCategorieAlimentation = new ArrayAdapter(this, android.R.layout.simple_spinner_item, IDepenseFixeConstantes.S_CATEGORIE_ALIMENTATION);
        adapterSousCategorieAlimentation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSousCategorie.setAdapter(adapterSousCategorieAlimentation);

        spinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String categorie = (String) adapterCategorie.getItem(position);
                setSpinnerSousCategorie(categorie, spinnerSousCategorie, adapterSousCategorieAlimentation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinner dropdown compte
        Spinner spinnerComptes = (Spinner) subView.findViewById(R.id.spinnerCompteVariable);
        ArrayAdapter adapterCompte = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listComptes);
        adapterCompte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComptes.setAdapter(adapterCompte);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une depense");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();
        //Ajouter les btns de l'alerte
        builder.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //Creer un objet DepensesVariable
                    DepenseVariable depenseVariable = new DepenseVariable();
                    depenseVariable.setIdDepenseVariable(listDepenseVariable.get(position).getIdCompte());
                    depenseVariable.setDescription(txtDescription.getText().toString());
                    depenseVariable.setMontant(Double.parseDouble(txtMontant.getText().toString()));
                    depenseVariable.setCategorie(IDepenseFixeConstantes.CATEGORIES_VARIABLE[spinnerCategorie.getSelectedItemPosition()]);
                    depenseVariable.setSousCategorie(String.valueOf(spinnerSousCategorie.getSelectedItem()));
                    depenseVariable.setDate(dateChoisi);
                    depenseVariable.setIdCompte(listComptes.get(spinnerComptes.getSelectedItemPosition()).getIdCompte());
                    depenseVariableDBAdapter.updateDepenseVariable(depenseVariable);
                    depenseVariableDBAdapter.close();
                    afficherDepensesVariable();
                    mettreAJourSoldeCompte(depenseVariable, montant);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DepensesVariableActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openDialogueDeleteDepense(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DepensesVariableActivity.this);
        builder.setTitle("Alert!");
        builder.setMessage("Voulez-vous vraiment supprimer la depense " + listDepenseVariable.get(position).getDescription() + "?");
        builder.setCancelable(false);
        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            DepenseVariable depenseVariable = depenseVariableDBAdapter.trouverDepenseVariableParId(listDepenseVariable.get(position).getIdDepenseVariable());
            Toast.makeText(DepensesVariableActivity.this, "Revenu supprimer", Toast.LENGTH_LONG).show();
            enleverSoldeCompte(depenseVariable);
            depenseVariableDBAdapter.deleteDepenseVariable(depenseVariable);
            depenseVariableDBAdapter.close();
            afficherDepensesVariable();
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            Toast.makeText(DepensesVariableActivity.this, "Annuler", Toast.LENGTH_LONG).show();
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void enleverSoldeCompte(DepenseVariable depenseVariable) {
        //Faire un update du solde du compte avec le montant du revenue
        Compte compte = compteDBAdapter.trouverCompteParId(depenseVariable.getIdCompte());
        double ancienSolde = compte.getSolde();
        double montant = depenseVariable.getMontant();
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

    private void mettreAJourSoldeCompte(DepenseVariable depenseVariable, Double montant) {
        //Faire un update du solde du compte avec le montant du revenue
        Compte compte = compteDBAdapter.trouverCompteParId(depenseVariable.getIdCompte());
        double ancienSolde = compte.getSolde();
        double nouveauMontant = depenseVariable.getMontant();
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

    private void setSpinnerSousCategorie(String categorie, Spinner spinnerSousCategorie, ArrayAdapter adapterSousCategorieAliments) {
        switch (categorie){
            case IDepenseFixeConstantes.ALIMENTATION:
                spinnerSousCategorie.setAdapter(adapterSousCategorieAliments);
                break;
            case IDepenseFixeConstantes.TABAC_ALCOOL:
                ArrayAdapter adapterTabac = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.TABAC_ALCOOL));
                spinnerSousCategorie.setAdapter(adapterTabac);
                break;
            case IDepenseFixeConstantes.VETEMENTS:
                ArrayAdapter adapterVetement = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.VETEMENTS));
                spinnerSousCategorie.setAdapter(adapterVetement);
                break;
            case IDepenseFixeConstantes.SANTE_BEAUTE:
                ArrayAdapter adapterSante = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.SANTE_BEAUTE));
                spinnerSousCategorie.setAdapter(adapterSante);
                break;
            case IDepenseFixeConstantes.ENFANTS:
                ArrayAdapter adapterEnfants = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.ENFANTS));
                spinnerSousCategorie.setAdapter(adapterEnfants);
                break;
            case IDepenseFixeConstantes.ANIMAL:
                ArrayAdapter adapterAnimal = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.ANIMAL));
                spinnerSousCategorie.setAdapter(adapterAnimal);
                break;
            case IDepenseFixeConstantes.MENAGER:
                ArrayAdapter adapterMenager = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.MENAGER));
                spinnerSousCategorie.setAdapter(adapterMenager);
                break;
            case IDepenseFixeConstantes.JOURNAL:
                ArrayAdapter adapterJournal = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.JOURNAL));
                spinnerSousCategorie.setAdapter(adapterJournal);
                break;
            case IDepenseFixeConstantes.SPORTS:
                ArrayAdapter adapterSports = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.SPORTS));
                spinnerSousCategorie.setAdapter(adapterSports);
                break;
            case IDepenseFixeConstantes.POCHE:
                ArrayAdapter adapterPoche = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.POCHE));
                spinnerSousCategorie.setAdapter(adapterPoche);
                break;
            case IDepenseFixeConstantes.CADEAUX:
                ArrayAdapter adapterCadeaux = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.CADEAUX));
                spinnerSousCategorie.setAdapter(adapterCadeaux);
                break;
            case IDepenseFixeConstantes.AUTRE_DEPENSES:
                ArrayAdapter adapterAutre = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Collections.singletonList(IDepenseFixeConstantes.AUTRE_DEPENSES));
                spinnerSousCategorie.setAdapter(adapterAutre);
                break;
        }
    }

    private void updateSoldeCompte(DepenseVariable depenseVariable) {
        //Faire un update du solde du compte avec le montant du revenue
        Compte compte = compteDBAdapter.trouverCompteParId(depenseVariable.getIdCompte());
        double ancienSolde = compte.getSolde();
        double montant = depenseVariable.getMontant();
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