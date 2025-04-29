package serveur.serveurjeux.Controller;

import serveur.serveurjeux.Entity.Competence.Competence;

import java.util.ArrayList;
import java.util.List;

public class GestionCompetence {
    private static List<Competence>  competences = new ArrayList<>();

    public static void AjouterCompetence(Competence competence) {
        competences.add(competence);
    }

    public static void RetirerCompetence(Competence competence) {
        if(competences.contains(competence)) {
            competences.remove(competence);
        }
    }

    public static List<Competence> getCompetences() {
        return competences;
    }
}
