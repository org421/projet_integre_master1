package serveur.serveurjeux.controllerReseau;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serveur.serveurjeux.Component.Reseau;
import serveur.serveurjeux.DTO.Envoie;
import serveur.serveurjeux.DTO.typeMessage.DemandeLoginSiteWeb;
import serveur.serveurjeux.DTO.typeReponse.*;
import serveur.serveurjeux.Entity.Entite;
import serveur.serveurjeux.Entity.Personnage;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Mapper.InventaireMapper;
import serveur.serveurjeux.Mapper.PersonnageMapper;
import serveur.serveurjeux.Repository.EntiteRepository;
import serveur.serveurjeux.Repository.PersonnageRepository;
import serveur.serveurjeux.Repository.UserRepository;
import serveur.serveurjeux.Service.UserService;
import serveur.serveurjeux.Entity.StatAdmin;

import java.io.IOException;

@Service
public class ActionReseau {

    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonnageRepository personnageRepository;
    @Autowired
    private EntiteRepository entiteRepository;

    @Autowired
    private PersonnageMapper personnageMapper;
    @Autowired
    private InventaireMapper inventaireMapper;


    public void receptionUUID(ReponseUUID reponse) {
        Reseau.UUID = reponse.getUuid();
        System.out.println(Reseau.UUID);
    }

    public void demandeInformationLogin(ReponseDemandeLoginSiteWeb reponse) throws IOException {
        int index = reponse.index;
        String pseudo = reponse.pseudo;
        DemandeLoginSiteWeb demande = new DemandeLoginSiteWeb();
        if(userService.verifPseudoPasswordAndAccess(pseudo, reponse.mdp)) {
            demande.valide = true;
            User user = userService.getUserByUsername(pseudo);
            Personnage personnage = user.getPersonnage();
            if(personnage != null) {
                serveur.serveurjeux.DTO.Entity.Personnage personnageDTO = personnageMapper.personnageToPersonnageDTO(personnage);
                demande.personnage = personnageDTO;
                demande.inventaireDTO = inventaireMapper.inventaireToIventaireDTO(personnage.getInventaire());
            } else {
                demande.personnage = null;
            }
            demande.pseudo = pseudo;
        } else {
            demande.valide = false;
            demande.personnage = null;
            demande.pseudo = null;
        }
        demande.index = index;

        Envoie envoie = new Envoie(Reseau.address, Reseau.UUID);
        envoie.ajouterMessage(demande);
        Reseau.writer.writeObject(envoie);
        Reseau.writer.flush();
    }

    public void demandeCreationPersonnage(ReponseCreationPersonnage reponse) throws IOException {
        System.out.println("test");
        String pseudo = reponse.pseudo;
        User user = userService.getUserByUsername(pseudo);
        Personnage personnage = new Personnage(reponse.personnage.getClasse());
        Entite entite = personnage.getEntite();
        personnage.setEntite(entite);
        user.setPersonnage(personnage);
        entiteRepository.save(entite);
        personnageRepository.save(personnage);
        userRepository.save(user);
    }

    public void receptionNbJoueur(ReponseNbJoueurs reponse) {
        StatAdmin.NbJoueurs = reponse.getNbJoueurs();
        System.out.println(StatAdmin.NbJoueurs);
    }

    public void sauvegardePersonnage(ReponseSauvegardePersonnage reponse){
        User user = userService.getUserByUsername(reponse.pseudo);
        if(user.getPersonnage() != null) {
            personnageMapper.personnageDTOToPersonnageAndSave(reponse.personnage,user.getPersonnage());
            inventaireMapper.inventaireDTOtoIventaire(reponse.inventaireDTO,user.getPersonnage());
        }
    }
}
