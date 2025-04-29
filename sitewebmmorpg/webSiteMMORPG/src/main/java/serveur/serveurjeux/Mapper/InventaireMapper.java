package serveur.serveurjeux.Mapper;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serveur.serveurjeux.DTO.Entity.CaseInventaireDTO;
import serveur.serveurjeux.DTO.Entity.InventaireDTO;
import serveur.serveurjeux.Entity.CaseInventaire;
import serveur.serveurjeux.Entity.Inventaire;
import serveur.serveurjeux.Entity.Personnage;
import serveur.serveurjeux.Repository.CaseInventaireRepository;
import serveur.serveurjeux.Repository.InventaireRepository;
import serveur.serveurjeux.Repository.PersonnageRepository;

import java.util.List;

@Service
public class InventaireMapper {
    @Autowired
    InventaireRepository inventaireRepository;
    @Autowired
    CaseInventaireRepository caseInventaireRepository;
    @Autowired
    private PersonnageRepository personnageRepository;

    public InventaireDTO inventaireToIventaireDTO(Inventaire inventaire) {
        InventaireDTO inventaireDTO = new InventaireDTO();
        List<CaseInventaire> listCaseInventaire = caseInventaireRepository.findByInventaire(inventaire);
        if(inventaire != null) {
            for (CaseInventaire caseInventaire : listCaseInventaire) {
                inventaireDTO.caseInventaire[caseInventaire.indexY][caseInventaire.indexX] = new CaseInventaireDTO();
                inventaireDTO.caseInventaire[caseInventaire.indexY][caseInventaire.indexX].type = caseInventaire.type;
                if (caseInventaire.type == CaseInventaireDTO.OBJET) {
                    inventaireDTO.caseInventaire[caseInventaire.indexY][caseInventaire.indexX].typeObjet = caseInventaire.typeObjet;
                }
                inventaireDTO.caseInventaire[caseInventaire.indexY][caseInventaire.indexX].id = caseInventaire.idJeux;
            }
        }
        return inventaireDTO;
    }

    public void inventaireDTOtoIventaire(InventaireDTO inventaireDTO, Personnage personnage) {
        if(personnage.getInventaire() != null){
//            Inventaire inventaire = personnage.getInventaire();
//            for(CaseInventaire caseInventaire : inventaire.getCaseInventaires()){
//                caseInventaireRepository.delete(caseInventaire);
//            }
            Inventaire oldInventaire = personnage.getInventaire();
            personnage.setInventaire(null); // On détache d'abord
            personnageRepository.save(personnage);
            inventaireRepository.delete(oldInventaire);
        }
        Inventaire inventaire = new Inventaire();
        if(inventaireDTO != null && inventaireDTO.caseInventaire != null) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 8; j++) {
                    if (inventaireDTO.caseInventaire[i][j] != null) {
                        System.out.println("La case "+i+" et "+j+" n'est pas vide !");
                        CaseInventaire caseInventaire = new CaseInventaire();
                        caseInventaire.type = inventaireDTO.caseInventaire[i][j].type;
                        caseInventaire.idJeux = inventaireDTO.caseInventaire[i][j].id;
                        caseInventaire.typeObjet = inventaireDTO.caseInventaire[i][j].typeObjet;
                        caseInventaire.indexY = i;
                        caseInventaire.indexX = j;
                        caseInventaire.setInventaire(inventaire);
                        inventaire.addCaseInventaire(caseInventaire);
                    }
                }
            }
        }
        inventaireRepository.save(inventaire);
        personnage.setInventaire(inventaire);
        personnageRepository.save(personnage);
    }
}
