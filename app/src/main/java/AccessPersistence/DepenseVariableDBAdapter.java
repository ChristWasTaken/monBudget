package AccessPersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

            if (cursor.moveToFirst()) {
                while(cursor.moveToNext()) {
                    DepenseVariable depenseVariable = getDepenseVariableFromCursor(cursor);
                    depenseVariableList.add(depenseVariable);
                }
            }
            return depenseVariableList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return depenseVariableList;
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
