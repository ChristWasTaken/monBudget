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
import model.DepenseFixe;
import model.DepenseVariable;

public class DepenseFixeDBAdapter {

    private SQLiteDatabase db;
    private CommonDBHelper dbHelper;
    private Context context;

    public DepenseFixeDBAdapter(Context context) {
        this.context = context;
        this.dbHelper = new CommonDBHelper(context, IDepenseFixeConstantes.BD_NOM, null, IDepenseFixeConstantes.VERSION);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    //Methode create
    public boolean ajouterDepenseFixe(DepenseFixe depenseFixe) {
        boolean resultat;
        try {
            open();

            ContentValues contentValues = getContentValuesDepenseFixe(depenseFixe);
            if(db.insert(IDepenseFixeConstantes.TABLE_DEPENSEFIXE, null, contentValues) != -1) {
                Toast.makeText(context, "Ajout dépense fixe Reussi", Toast.LENGTH_LONG).show();
                resultat = true;
            } else {
                Toast.makeText(context, "Ajout Dépense fixe Échoué", Toast.LENGTH_LONG).show();
                resultat = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            resultat = false;
        }
        return resultat;
    }

    //Methode findAll
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<DepenseFixe> findAllDepensesFixes() {
        ArrayList<DepenseFixe> depensesFixes = new ArrayList<>();
        try {
            open();
            Cursor cursor = db.query(IDepenseFixeConstantes.TABLE_DEPENSEFIXE, IDepenseFixeConstantes.COLONNES,
                    null, null, null, null, IDepenseFixeConstantes.COL_DATE);

            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    DepenseFixe depenseFixe = getDepenseFixeFromCursor(cursor);
                    depensesFixes.add(depenseFixe);
                }
            }
            return depensesFixes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Methode find
    @RequiresApi(api = Build.VERSION_CODES.O)
    public DepenseFixe trouverDepenseFixeParId(int id){
        try {
            open();
            DepenseFixe depenseFixe = new DepenseFixe();
            Cursor cursor = db.query(IDepenseFixeConstantes.TABLE_DEPENSEFIXE, IDepenseFixeConstantes.COLONNES,
                    IDepenseFixeConstantes.COL_ID + " = " + id, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                depenseFixe = getDepenseFixeFromCursor(cursor);
            }
            return depenseFixe;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Methode update
    public boolean updateDepenseFixe(DepenseFixe depenseFixe){
        try {
            open();
            ContentValues contentValues = getContentValuesDepenseFixe(depenseFixe);
            db.update(IDepenseFixeConstantes.TABLE_DEPENSEFIXE, contentValues, IDepenseFixeConstantes.COL_ID
                    + " = ?", new String[] {String.valueOf(depenseFixe.getIdDepenseFixe())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //Methode delete
    public boolean deleteDepenseFixe(DepenseFixe depenseFixe){
        try {
            open();
            db.delete(IDepenseFixeConstantes.TABLE_DEPENSEFIXE, IDepenseFixeConstantes.COL_ID
                    + " = ?", new String[] {String.valueOf(depenseFixe.getIdDepenseFixe())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private DepenseFixe getDepenseFixeFromCursor(Cursor cursor) {
        DepenseFixe depenseFixe = new DepenseFixe();
        depenseFixe.setIdDepenseFixe(cursor.getInt(0));
        depenseFixe.setDescription(cursor.getString(1));
        depenseFixe.setMontant(cursor.getDouble(2));
        depenseFixe.setCategorie(cursor.getString(3));
        depenseFixe.setSousCategorie(cursor.getString(4));
        depenseFixe.setFrequence(cursor.getInt(5));
        depenseFixe.setDate(LocalDate.parse(cursor.getString(6)));
        depenseFixe.setIdCompte(cursor.getInt(7));
        return depenseFixe;
    }

    @NonNull
    private ContentValues getContentValuesDepenseFixe(DepenseFixe depenseFixe) {
        String description = depenseFixe.getDescription();
        double montant = depenseFixe.getMontant();
        String categorie = depenseFixe.getCategorie();
        String sousCategorie = depenseFixe.getSousCategorie();
        int frequence = depenseFixe.getFrequence();
        String date = depenseFixe.getDate().toString();
        int idCompte = depenseFixe.getIdCompte();

        ContentValues contentValues = new ContentValues();
        contentValues.put(IDepenseFixeConstantes.COL_DESCRIPTION, description);
        contentValues.put(IDepenseFixeConstantes.COL_MONTANT, montant);
        contentValues.put(IDepenseFixeConstantes.COL_CATEGORIE, categorie);
        contentValues.put(IDepenseFixeConstantes.COL_SOUSCATEGORIE, sousCategorie);
        contentValues.put(IDepenseFixeConstantes.COL_FREQUENCE, frequence);
        contentValues.put(IDepenseFixeConstantes.COL_DATE, date);
        contentValues.put(IDepenseFixeConstantes.COL_IDCOMPTE, idCompte);
        return contentValues;
    }
}
