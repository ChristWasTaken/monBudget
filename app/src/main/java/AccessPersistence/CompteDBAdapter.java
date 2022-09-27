package AccessPersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import model.Compte;

/**
 * Class pour operation CRUD pour les comptes
 */
public class CompteDBAdapter {
    private SQLiteDatabase db;
    private CompteDBHelper compteDBHelper;
    private Context context;

    public CompteDBAdapter(Context context){
        this.context = context;
        this.compteDBHelper = new CompteDBHelper(context, CompteDBHelper.BD_NOM, null, CompteDBHelper.VERSION);
    }

    public void open(){
        db = compteDBHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    //Methode create
    public boolean ajouterCompte(Compte compte){
        boolean result = false;
        try{
            open();

            ContentValues contentValues = getContentValuesCompte(compte);

            db.insert(CompteDBHelper.TABLE_COMPTE, null, contentValues);
            Toast.makeText(context, "Ajout Reussi", Toast.LENGTH_LONG).show();

            result = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @NonNull
    private ContentValues getContentValuesCompte(Compte compte) {
        String description = compte.getDescription();
        double solde = compte.getSolde();
        String type = compte.getType();
        String institution = compte.getInstitution();
        int numCompte = compte.getNumCompte();
        int numSuccursale = compte.getNumSuccursale();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CompteDBHelper.COL_DESCRIPTION, description);
        contentValues.put(CompteDBHelper.COL_SOLDE, solde);
        contentValues.put(CompteDBHelper.COL_TYPE, type);
        contentValues.put(CompteDBHelper.COL_INSTITUTION, institution);
        contentValues.put(CompteDBHelper.COL_NUMCOMPTE, numCompte);
        contentValues.put(CompteDBHelper.COL_NUMSUCCURSALE, numSuccursale);
        return contentValues;
    }

    //Methode findAll
    public List<Compte> findAllComptes(){
        ArrayList<Compte> listComptes = new ArrayList<>();
        try {
            open();
            Cursor cursor = db.rawQuery("SELECT * FROM " + CompteDBHelper.TABLE_COMPTE + " ORDER BY " + CompteDBHelper.COL_ID, null);
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    Compte compte = new Compte();
                    compte.setIdCompte(cursor.getInt(0));
                    compte.setDescription(cursor.getString(1));
                    compte.setSolde(cursor.getDouble(2));
                    compte.setType(cursor.getString(3));
                    compte.setInstitution(cursor.getString(4));
                    compte.setNumCompte(cursor.getInt(5));
                    compte.setNumSuccursale(cursor.getInt(6));
                    listComptes.add(compte);
                    cursor.moveToNext();
                }
            }
            return listComptes;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Methode find
    public Compte trouverCompteParId(int id){
        try {
            open();
            Compte compte = new Compte();
            Cursor cursor = db.rawQuery("SELECT * FROM " + CompteDBHelper.TABLE_COMPTE + " WHERE " + CompteDBHelper.COL_ID + " = " + String.valueOf(id), null);
            if (cursor != null) {
                cursor.moveToFirst();
                compte.setIdCompte(cursor.getInt(0));
                compte.setDescription(cursor.getString(1));
                compte.setSolde(cursor.getDouble(2));
                compte.setType(cursor.getString(3));
                compte.setInstitution(cursor.getString(4));
                compte.setNumCompte(cursor.getInt(5));
                compte.setNumSuccursale(cursor.getInt(6));
            }
            return compte;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Methode update
    public boolean updateCompte(Compte compte){
        try {
            open();
            ContentValues contentValues = getContentValuesCompte(compte);
            db.update(CompteDBHelper.TABLE_COMPTE, contentValues, CompteDBHelper.COL_ID + " = ?", new String[] {String.valueOf(compte.getIdCompte())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Methode delete
    public boolean deleteCompte(Compte compte){
        try {
            open();
            db.delete(CompteDBHelper.TABLE_COMPTE, CompteDBHelper.COL_ID + " = ?", new String[]{String.valueOf(compte.getIdCompte())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
