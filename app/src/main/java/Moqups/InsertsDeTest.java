package Moqups;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Compte;
import model.DepenseFixe;
import model.DepenseVariable;
import model.Revenue;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InsertsDeTest {

    static String[] CATEGORIES = {"Habitation", "Services publics", "Assurance",
            "Emprunts", "Dépenses variables", "Autres"};

    static Compte compteBnc = new Compte("Banque Nationale", 1000.00,
            "Chequing", "Banque Nationale", 3424322, 456);
    static Compte compteBmo = new Compte("Banque Montréal", 500.00,
            "Chequing", "Banque Montréal", 123456, 123);

    static DepenseFixe DepenseFixe = new DepenseFixe("Loyer", 500.00, CATEGORIES[0],
            "Loyer", 0, LocalDate.of(2022, 10, 1), 1);
    static DepenseFixe DepenseFixe2 = new DepenseFixe("Hydro_Québec", 75.00, CATEGORIES[1],
            "Loyer", 0, LocalDate.of(2022, 10, 15), 1);
    static DepenseFixe DepenseFixe3 = new DepenseFixe("Vidéotron", 125.00, CATEGORIES[1],
            "Loyer", 0, LocalDate.of(2022, 10, 18), 1);
    static DepenseFixe DepenseFixe4 = new DepenseFixe("Assurances Beneva", 128.77, CATEGORIES[2],
            "Loyer", 0, LocalDate.of(2022, 9, 19), 1);
    static DepenseFixe DepenseFixe5 = new DepenseFixe("Immatriculation", 18.26, CATEGORIES[2],
            "Loyer", 0, LocalDate.of(2022, 10, 27), 1);
    static DepenseFixe DepenseFixe6 = new DepenseFixe("Pret automobile", 350.00, CATEGORIES[3],
            "Loyer", 0, LocalDate.of(2022, 10, 12), 1);

    static DepenseVariable depenseVariable1 = new DepenseVariable("Achat", 23.23,
            "Alimentation", "Épicerie", LocalDate.of(2022, 8, 1), 1);
    static DepenseVariable depenseVariable2 = new DepenseVariable("Achat", 175.00,
            "Alimentation", "Épicerie", LocalDate.of(2022, 10, 1), 1);
    static DepenseVariable depenseVariable3 = new DepenseVariable("Achat", 125.00,
            "Alimentation", "Épicerie", LocalDate.of(2022, 10, 1), 1);
    static DepenseVariable depenseVariable4 = new DepenseVariable("Achat", 50.00,
            "Alimentation", "Épicerie", LocalDate.of(2022, 9, 1), 1);
    static DepenseVariable depenseVariable5 = new DepenseVariable("Achat", 40.00,
            "Alimentation", "Épicerie", LocalDate.of(2022, 9, 2), 1);
    static DepenseVariable depenseVariable6 = new DepenseVariable("Achat", 30.00,
            "Entretien ménagé", "Achat", LocalDate.of(2022, 10, 1), 1);
    static DepenseVariable depenseVariable7 = new DepenseVariable("Achat", 50.00,
            "Entretien ménagé", "Achat", LocalDate.of(2022, 10, 14), 1);
    static DepenseVariable depenseVariable8 = new DepenseVariable("Achat", 20.00,
            "Entretien ménagé", "Achat", LocalDate.of(2022, 9, 14), 1);
    static DepenseVariable depenseVariable9 = new DepenseVariable("Achat", 10.00,
            "Entretien ménagé", "Achat", LocalDate.of(2022, 9, 1), 1);

    static Revenue revenue1 = new Revenue("Salaire", 1000.00, "Salaire",
            2, LocalDate.of(2022, 9, 1), 1);
    static Revenue revenue2 = new Revenue("test", 10.00, "Salaire",
            1, LocalDate.of(2022, 8, 15), 1);
    static Revenue revenue6 = new Revenue("Remboursement TVQ", 123.78, "Remboursement impot",
            4, LocalDate.of(2022, 6, 15), 1);

    public static List<model.Compte> getComptes(){
        List<model.Compte> comptes = new ArrayList<>();
        comptes.add(compteBnc);
        comptes.add(compteBmo);
        return comptes;
    }

    public static List<model.DepenseFixe> getDepensesFixes(){
        List<DepenseFixe> depensesFixes = new ArrayList<>();
        depensesFixes.add(DepenseFixe);
        depensesFixes.add(DepenseFixe2);
        depensesFixes.add(DepenseFixe3);
        depensesFixes.add(DepenseFixe4);
        depensesFixes.add(DepenseFixe5);
        depensesFixes.add(DepenseFixe6);
        return depensesFixes;
    };

    public static List<DepenseVariable> getDepensesVariables(){
        List<DepenseVariable> depensesVariables = new ArrayList<>();
        depensesVariables.add(depenseVariable1);
        depensesVariables.add(depenseVariable2);
        depensesVariables.add(depenseVariable3);
        depensesVariables.add(depenseVariable4);
        depensesVariables.add(depenseVariable5);
        depensesVariables.add(depenseVariable6);
        depensesVariables.add(depenseVariable7);
        depensesVariables.add(depenseVariable8);
        depensesVariables.add(depenseVariable9);
        return depensesVariables;
    };

    public static List<Revenue> getRevenues(){
        List<Revenue> revenues = new ArrayList<>();
        revenues.add(revenue1);
        revenues.add(revenue2);
//        revenues.add(revenue3);
//        revenues.add(revenue4);
//        revenues.add(revenue5);
        revenues.add(revenue6);
        return revenues;
    };
}
