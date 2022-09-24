package AccessPersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

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

            db.insert(CompteDBHelper.TABLE_COMPTE, null, contentValues);
            Toast.makeText(context, "Ajout Reussi", Toast.LENGTH_LONG).show();

            close();
            result = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //Methode findAll

    //Methode find

    //Methode update

    //Methode delete
}
