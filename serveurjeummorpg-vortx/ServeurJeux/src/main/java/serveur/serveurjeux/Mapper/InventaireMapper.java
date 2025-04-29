package serveur.serveurjeux.Mapper;

import serveur.serveurjeux.DTO.Client.InventaireDTOClient;
import serveur.serveurjeux.DTO.Entity.CaseInventaireDTO;
import serveur.serveurjeux.DTO.Entity.InventaireDTO;
import serveur.serveurjeux.Entity.Competence.Competence;
import serveur.serveurjeux.Entity.Inventaire.CaseInventaire;
import serveur.serveurjeux.Entity.Inventaire.Inventaire;
import serveur.serveurjeux.Entity.Item.Arme.Epee;
import serveur.serveurjeux.Entity.Item.Armure.Bottes;
import serveur.serveurjeux.Entity.Item.Armure.Casque;
import serveur.serveurjeux.Entity.Item.Armure.Gants;
import serveur.serveurjeux.Entity.Item.Armure.Plastron;
import serveur.serveurjeux.Entity.Item.Consommable.Potion;
import serveur.serveurjeux.Entity.Item.Objet;
import serveur.serveurjeux.Entity.Item.ObjetClassique;
import serveur.serveurjeux.DTO.Client.caseInventaireDTOClient;

public class InventaireMapper {

    public InventaireDTOClient inventaireToInventaireDTOClient(Inventaire inventaire) {
        InventaireDTOClient inventaireDTO = new InventaireDTOClient();
        for (int i=0; i<5; i++){
            for (int j=0; j<8; j++){
                if(inventaire.inventaire[i][j] != null && !inventaire.inventaire[i][j].isVide()){
                    inventaireDTO.inventaireDTOClients[i][j] = new caseInventaireDTOClient();
                    inventaireDTO.inventaireDTOClients[i][j].x = j;
                    inventaireDTO.inventaireDTOClients[i][j].y = i;
                    if(inventaire.inventaire[i][j].getObjet() != null){
                        inventaireDTO.inventaireDTOClients[i][j].type = CaseInventaireDTO.OBJET;
                        inventaireDTO.inventaireDTOClients[i][j].typeObjet = inventaire.inventaire[i][j].getObjet().getType();
                        inventaireDTO.inventaireDTOClients[i][j].id = inventaire.inventaire[i][j].getObjet().getId();
                        inventaireDTO.inventaireDTOClients[i][j].nom = inventaire.inventaire[i][j].getObjet().getNom();
                        inventaireDTO.inventaireDTOClients[i][j].description = inventaire.inventaire[i][j].getObjet().getDescription();
                    } else if(inventaire.inventaire[i][j].getCompetence() != null){
                        inventaireDTO.inventaireDTOClients[i][j].type = CaseInventaireDTO.COMPETENCE;
                        inventaireDTO.inventaireDTOClients[i][j].id = inventaire.inventaire[i][j].getCompetence().getId();
                        inventaireDTO.inventaireDTOClients[i][j].nom = inventaire.inventaire[i][j].getCompetence().getNom();
                        inventaireDTO.inventaireDTOClients[i][j].description = inventaire.inventaire[i][j].getCompetence().getDescription();
                    }
                }
            }
        }
        return inventaireDTO;
    }

    public Inventaire inventaireDTOtoInventaire(InventaireDTO inventaireDTO) {
        Inventaire inventaire = new Inventaire();
        for (int i=0; i < 5; i++){
            for (int j=0; j < 8; j++){
                inventaire.inventaire[i][j] = new CaseInventaire();
                if(inventaireDTO.caseInventaire[i][j] != null){
                    if(inventaireDTO.caseInventaire[i][j].type == CaseInventaireDTO.OBJET){
                        Objet objet = this.constructObjet(inventaireDTO.caseInventaire[i][j].typeObjet,inventaireDTO.caseInventaire[i][j].id);
                        if( objet != null){
                            inventaire.inventaire[i][j].ajouterObjet(objet);
                        }
                    } else if(inventaireDTO.caseInventaire[i][j].type == CaseInventaireDTO.COMPETENCE){
                        inventaire.inventaire[i][j].ajouterCompetence(new Competence(inventaireDTO.caseInventaire[i][j].id));
                    }
                }
            }
        }
        return inventaire;
    }

    public InventaireDTO inventaireToInventaireDTO(Inventaire inventaire) {
        InventaireDTO inventaireDTO = new InventaireDTO();
        for (int i=0; i < 5; i++){
            for (int j=0; j < 8; j++){
                inventaireDTO.caseInventaire[i][j] = new CaseInventaireDTO();
                if(inventaire != null && inventaire.inventaire[i][j] != null && !inventaire.inventaire[i][j].isVide()){
                    if (inventaire.inventaire[i][j].getCompetence() != null){
                        inventaireDTO.caseInventaire[i][j].type = CaseInventaireDTO.COMPETENCE;
                        inventaireDTO.caseInventaire[i][j].id = inventaire.inventaire[i][j].getCompetence().getId();
                    } else if (inventaire.inventaire[i][j].getObjet() != null){
                        inventaireDTO.caseInventaire[i][j].type = CaseInventaireDTO.OBJET;
                        inventaireDTO.caseInventaire[i][j].typeObjet = inventaire.inventaire[i][j].getObjet().getType();
                        inventaireDTO.caseInventaire[i][j].id = inventaire.inventaire[i][j].getObjet().getId();
                    }
                    System.out.println("La case "+i+" et "+j+" est pas vide !");
                } else {
                    System.out.println("La case "+i+" et "+j+" est vide !");

                }
            }
        }
        return inventaireDTO;
    }

    public Objet constructObjet(int type, int id){
        switch (type){
            case 1:
                Epee epee = new Epee(id);
                return epee;
            case 2:
                Casque casque = new Casque(id);
                return casque;
            case 3:
                Plastron plastron = new Plastron(id);
                return plastron;
            case 4:
                Bottes bottes = new Bottes(id);
                return bottes;
            case 5:
                Gants gants = new Gants(id);
                return gants;
            case 6:
                Potion potion = new Potion(id);
                return potion;
            case 7:
                ObjetClassique objetClassique = new ObjetClassique(id);
                return objetClassique;
        }
        return null;
    }
}
