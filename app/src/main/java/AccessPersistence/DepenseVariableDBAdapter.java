package AccessPersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Compte;
import model.DepenseVariable;

public class DepenseVariableDBAdapter {

    SQLiteDatabase db;
    CommonDBHelper dbHelper;
    private Context context;

    public DepenseVariableDBAdapter(Context context) {
        this.context = context;
        this.dbHelper = new CommonDBHelper(context, IDepenseVariableConstantes.BD_NOM, null, IDepenseVariableConstantes.VERSION);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    //Methode create
    public boolean ajouterDepenseVariable(DepenseVariable depenseVariable) {
        boolean resultat = false;
        try {
            open();

            ContentValues contentValues = getContentValuesDepenseVariable(depenseVariable);
            if(db.insert(IDepenseVariableConstantes.TABLE_DEPENSEVARIABLE, null, contentValues) != -1) {
                Toast.makeText(context, "Ajout Dépense variable Reussi", Toast.LENGTH_LONG).show();
                resultat = true;
            } else {
                Toast.makeText(context, "Ajout Dépense variable Echoué", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return resultat;
    }

    //Methode findAll
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DepenseVariable> findAll() {
        List<DepenseVariable> depenseVariableList = new ArrayList<>();
        try {
            open();
            Cursor cursor = db.query(IDepenseVariableConstantes.TABLE_DEPENSEVARIABLE, IDepenseVariableConstantes.COLONNES,
                    null, null, null, null, IDepenseFixeConstantes.COL_DATE);

            cursor.moveToFirst();
            if (cursor != null && cursor.getCount() > 0) {
                DepenseVariable depenseVariable = getDepenseVariableFromCursor(cursor);
                depenseVariableList.add(depenseVariable);
                while(cursor.moveToNext()) {
                    depenseVariable = getDepenseVariableFromCursor(cursor);
                    depenseVariableList.add(depenseVariable);
                }
            }
            return depenseVariableList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return depenseVariableList;
    }

    //Méthode find pour les dépenses fixes par mois
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DepenseVariable> findDepenseVariableByMonth(LocalDate date) {
        List<DepenseVariable> depenseVariableList = new ArrayList<>();

        int month = date.getMonthValue();
        DecimalFormat df = new DecimalFormat("00");
        String formattedMonth = df.format(month);

        try {
            open();
            Cursor cursor = db.query(IDepenseVariableConstantes.TABLE_DEPENSEVARIABLE, IDepenseVariableConstantes.COLONNES,
                    "STRFTIME('%m', " + IDepenseVariableConstantes.COL_DATE + ") = '" + formattedMonth + "'", null, null, null, IDepenseVariableConstantes.COL_DATE);

            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                DepenseVariable depenseVariable = getDepenseVariableFromCursor(cursor);
                depenseVariableList.add(depenseVariable);
                while(cursor.moveToNext()) {
                    depenseVariable = getDepenseVariableFromCursor(cursor);
                    depenseVariableList.add(depenseVariable);
                }
            }
            close();
            return depenseVariableList;
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("log", "findDepenseVariableByMonth: " + e.getMessage());
            close();
        }
        close();
        return null;
    }

    //Methode find
    @RequiresApi(api = Build.VERSION_CODES.O)
    public DepenseVariable trouverDepenseVariableParId(int id){
        try {
            open();
            DepenseVariable depenseVariable = new DepenseVariable();
            Cursor cursor = db.query(IDepenseVariableConstantes.TABLE_DEPENSEVARIABLE, IDepenseVariableConstantes.COLONNES,
                    IDepenseVariableConstantes.COL_ID + " = " + id, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                depenseVariable = getDepenseVariableFromCursor(cursor);
            }
            return depenseVariable;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Methode update
    public boolean updateDepenseVariable(DepenseVariable depenseVariable){
        try {
            open();
            ContentValues contentValues = getContentValuesDepenseVariable(depenseVariable);
            db.update(IDepenseVariableConstantes.TABLE_DEPENSEVARIABLE, contentValues, IDepenseVariableConstantes.COL_ID
                    + " = ?", new String[] {String.valueOf(depenseVariable.getIdDepenseVariable())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //Methode delete
    public boolean deleteDepenseVariable(DepenseVariable depenseVariable){
        try {
            open();
            db.delete(IDepenseVariableConstantes.TABLE_DEPENSEVARIABLE, IDepenseVariableConstantes.COL_ID
                    + " = ?", new String[] {String.valueOf(depenseVariable.getIdDepenseVariable())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private ContentValues getContentValuesDepenseVariable(DepenseVariable depenseVariable) {
        String description = depenseVariable.getDescription();
        double montant = depenseVariable.getMontant();
        String categorie = depenseVariable.getCategorie();
        String sousCategorie = depenseVariable.getSousCategorie();
        String date = depenseVariable.getDate().toString();
        int idCompte = depenseVariable.getIdCompte();

        ContentValues contentValues = new ContentValues();
        contentValues.put(IDepenseVariableConstantes.COL_DESCRIPTION, description);
        contentValues.put(IDepenseVariableConstantes.COL_MONTANT, montant);
        contentValues.put(IDepenseVariableConstantes.COL_CATEGORIE, categorie);
        contentValues.put(IDepenseVariableConstantes.COL_SOUSCATEGORIE, sousCategorie);
        contentValues.put(IDepenseVariableConstantes.COL_DATE, date);
        contentValues.put(IDepenseVariableConstantes.COL_IDCOMPTE, idCompte);
        return contentValues;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private DepenseVariable getDepenseVariableFromCursor(Cursor cursor) {
        DepenseVariable depenseVariable = new DepenseVariable();
        depenseVariable.setIdDepenseVariable(cursor.getInt(0));
        depenseVariable.setDescription(cursor.getString(1));
        depenseVariable.setMontant(cursor.getDouble(2));
        depenseVariable.setCategorie(cursor.getString(3));
        depenseVariable.setSousCategorie(cursor.getString(4));
        depenseVariable.setDate(LocalDate.parse(cursor.getString(5)));
        depenseVariable.setIdCompte(cursor.getInt(6));
        return depenseVariable;
    }

}
