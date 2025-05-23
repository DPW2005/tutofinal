package org.example.demofinal.medecin ;

import java.util.List;

public class ExpertSystem {

    public static String diagnostiquer(List<String> s) {
        boolean f = s.contains("Fievre");
        boolean t = s.contains("Toux");
        boolean fa = s.contains("Fatigue");
        boolean m = s.contains("Maux de tete");
        boolean n = s.contains("Nausees");

        if (f && t && fa && m && n)
            return "Grippe sévère, COVID-19 ou dengue \n Consulter un médecin, traitement symptomatique" ;

        if (f && t && fa)
            return "Grippe saisonnière \n Repos, antipyrétique, hydratation" ;

        if (f && m && n)
            return "Gastro-entérite virale \n Réhydratation, diète légère" ;

        if (t && fa)
            return "Bronchite légère \n Repos, sirop contre la toux" ;

        if (f && m)
            return "Méningite virale (à surveiller) \n Consulter si aggravation" ;

        if (t && m)
            return "Sinusite ou irritation nasale \n Lavage nasal, antalgiques";

        if (fa && m)
            return "Surcharge mentale ou migraine \n Repos, hydratation" ;

        if (t && n)
            return "Toux irritative ou reflux \n Sirop antitussif, régime léger" ;

        if (fa && n)
            return "Anémie ou surmenage \n Repos, supplémentation";

        if (f && n)
            return "Grippe intestinale \n Paracétamol, réhydratation" ;

        if (f && t)
            return "Grippe ou COVID léger \n Repos, antipyrétique";

        if (f)
            return "Fièvre isolée \n Paracétamol, repos" ;

        if (t)
            return "Rhinopharyngite ou allergie \n Sirop, hydratation" ;

        if (fa)
            return "Fatigue passagère \n Repos, vitamines" ;

        if (m)
            return "Maux de tête \n Antalgique, calme" ;

        if (n)
            return "Indigestion \n Hydratation, alimentation légère";

        return "Aucun symptôme significatif \n Aucun traitement requis" ;
    }
}
