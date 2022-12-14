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
    private CommonDBHelper dbHelper;
    private Context context;

    public CompteDBAdapter(Context context){
        this.context = context;
        this.dbHelper = new CommonDBHelper(context, ICompteConstantes.BD_NOM, null, ICompteConstantes.VERSION);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    //Methode create
    public boolean ajouterCompte(Compte compte){
        boolean resultat = false;
        try{
            open();

            ContentValues contentValues = getContentValuesCompte(compte);

            if(db.insert(ICompteConstantes.TABLE_COMPTE, null, contentValues) != -1){
                Toast.makeText(context, "Ajout Compte Reussi", Toast.LENGTH_LONG).show();
                resultat = true;
            } else {
                Toast.makeText(context, "Ajout Compte Echoué", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return resultat;
    }

    //Methode findAll
    public List<Compte> findAllComptes(){
        ArrayList<Compte> listComptes = new ArrayList<>();
        try {
            open();
            Cursor cursor = db.query(ICompteConstantes.TABLE_COMPTE, ICompteConstantes.COLONNES,
                    null, null, null, null, ICompteConstantes.COL_ID);
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    Compte compte = getCompteFromCursor(cursor);
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
            Cursor cursor = db.query(ICompteConstantes.TABLE_COMPTE, ICompteConstantes.COLONNES,
                    ICompteConstantes.COL_ID + " = " + id, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                compte = getCompteFromCursor(cursor);
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
            db.update(ICompteConstantes.TABLE_COMPTE, contentValues, ICompteConstantes.COL_ID
                    + " = ?", new String[] {String.valueOf(compte.getIdCompte())});
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
            db.delete(ICompteConstantes.TABLE_COMPTE, ICompteConstantes.COL_ID
                    + " = ?", new String[]{String.valueOf(compte.getIdCompte())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @NonNull
    private Compte getCompteFromCursor(Cursor cursor) {
        Compte compte = new Compte();
        compte.setIdCompte(cursor.getInt(0));
        compte.setDescription(cursor.getString(1));
        compte.setSolde(cursor.getDouble(2));
        compte.setType(cursor.getString(3));
        compte.setInstitution(cursor.getString(4));
        compte.setNumCompte(cursor.getInt(5));
        compte.setNumSuccursale(cursor.getInt(6));
        return compte;
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
        contentValues.put(ICompteConstantes.COL_DESCRIPTION, description);
        contentValues.put(ICompteConstantes.COL_SOLDE, solde);
        contentValues.put(ICompteConstantes.COL_TYPE, type);
        contentValues.put(ICompteConstantes.COL_INSTITUTION, institution);
        contentValues.put(ICompteConstantes.COL_NUMCOMPTE, numCompte);
        contentValues.put(ICompteConstantes.COL_NUMSUCCURSALE, numSuccursale);
        return contentValues;
    }
}
