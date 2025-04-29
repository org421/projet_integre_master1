package serveur.serveurjeux.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serveur.serveurjeux.Entity.Personnage;
import serveur.serveurjeux.Entity.Entite;
import serveur.serveurjeux.Repository.EntiteRepository;
import serveur.serveurjeux.Repository.PersonnageRepository;

@Service
public class PersonnageMapper {

    @Autowired
    EntiteRepository entiteRepository;

    @Autowired
    PersonnageRepository personnageRepository;

    public serveur.serveurjeux.DTO.Entity.Personnage personnageToPersonnageDTO(Personnage personnage) {
        serveur.serveurjeux.DTO.Entity.Entite entite = new serveur.serveurjeux.DTO.Entity.Entite();
        entite.setId(personnage.getEntite().getId());
        entite.setLevel(personnage.getEntite().getLevel());
        entite.setPv(personnage.getEntite().getPv());
        entite.setInfluanceKarma(personnage.getEntite().getInfluanceKarma());
        entite.setVitesse(personnage.getEntite().getVitesse());
        entite.setPointArmure(personnage.getEntite().getPointArmure());
        entite.setxMatrice(personnage.getEntite().getxMatrice());
        entite.setyMatrice(personnage.getEntite().getyMatrice());
        entite.setXp(personnage.getEntite().getXp());
        entite.setX(personnage.getEntite().getX());
        entite.setY(personnage.getEntite().getY());
        entite.setPointAttaque(personnage.getEntite().getPointAttaque());
        entite.setPvMax(personnage.getEntite().getPvMax());

        serveur.serveurjeux.DTO.Entity.Personnage personnageDTO = new serveur.serveurjeux.DTO.Entity.Personnage();
        personnageDTO.setId(personnage.getId());
        personnageDTO.setArgent(personnage.getArgent());
        personnageDTO.setPointPersonnage(personnage.getPointPersonnage());
        personnageDTO.setClasse(personnage.getClasse());
        personnageDTO.setKarma(personnage.getKarma());
        personnageDTO.setRage(personnage.getRage());
        personnageDTO.setPointCompetence(personnage.getPointCompetence());
        personnageDTO.setEntite(entite);

        return personnageDTO;
    }

    public void personnageDTOToPersonnageAndSave(serveur.serveurjeux.DTO.Entity.Personnage personnageDTO, Personnage personnage){
        personnage.getEntite().setLevel(personnageDTO.getEntite().getLevel());
        personnage.getEntite().setPv(personnageDTO.getEntite().getPv());
        personnage.getEntite().setY(personnageDTO.getEntite().getY());
        personnage.getEntite().setX(personnageDTO.getEntite().getX());
        personnage.getEntite().setInfluanceKarma(personnageDTO.getEntite().getInfluanceKarma());
        personnage.getEntite().setPointArmure(personnageDTO.getEntite().getPointArmure());
        personnage.getEntite().setPointAttaque(personnageDTO.getEntite().getPointAttaque());
        personnage.getEntite().setPvMax(personnageDTO.getEntite().getPvMax());
        personnage.getEntite().setVitesse(personnageDTO.getEntite().getVitesse());
        personnage.getEntite().setxMatrice(personnageDTO.getEntite().getxMatrice());
        personnage.getEntite().setyMatrice(personnageDTO.getEntite().getyMatrice());
        personnage.getEntite().setXp(personnageDTO.getEntite().getXp());
        entiteRepository.save(personnage.getEntite());
        personnage.setArgent(personnageDTO.getArgent());
        personnage.setPointPersonnage(personnageDTO.getPointPersonnage());
        personnage.setClasse(personnageDTO.getClasse());
        personnage.setKarma(personnageDTO.getKarma());
        personnage.setRage(personnageDTO.getRage());
        personnage.setPointCompetence(personnageDTO.getPointCompetence());
        personnageRepository.save(personnage);
    }

}
