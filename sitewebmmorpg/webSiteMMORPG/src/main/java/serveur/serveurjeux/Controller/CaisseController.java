package serveur.serveurjeux.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import serveur.serveurjeux.Entity.*;
import serveur.serveurjeux.Repository.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class CaisseController {

    @Autowired
    private CaisseRepository caisseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultatOuvertureCaisseRepository resultatOuvertureCaisse;

    @Autowired
    private InventaireRepository inventaireRepository;

    @Autowired
    private CaseInventaireRepository caseInventaireRepository;


    @RequestMapping(value = "/caisses", method = RequestMethod.GET)
    public String caisses(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String pseudo = authentication.getName();
            User user = userRepository.findByUsername(pseudo);
            model.addAttribute("user", user);
            model.addAttribute("tokens", user.getTokens());
        }

        List<Caisse> caisses = caisseRepository.findAll();
        model.addAttribute("caisses", caisses);
        model.addAttribute("content", "caisses.html");
        return "base";
    }

    @RequestMapping(value = "/caisses/save-result", method = RequestMethod.POST)
    public @ResponseBody String saveResult(@RequestBody ResultatOuvertureCaisse resultat) {
        System.out.println("ID USER CAISSE : " + resultat.getUserId());

        // Récupérer l'utilisateur connecté via Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String pseudo = auth.getName();
            User user = userRepository.findByUsername(pseudo);
            if (user != null) {
                resultat.setUserId(user.getId());
                System.out.println("Utilisateur récupéré : " + user);
            }
        }

        // Afficher les informations du résultat avant sauvegarde
        System.out.println("Résultat à sauvegarder : "
                + "caisseId=" + resultat.getCaisseId()
                + ", itemName=" + resultat.getItemName()
                + ", itemImage=" + resultat.getItemImage()
                + ", itemType=" + resultat.getItemType()
                + ", typeObjet=" + resultat.getTypeObjet()  // 🔥 AJOUT ICI
                + ", userId=" + resultat.getUserId());

        // Sauvegarde du résultat dans la base de données
        resultatOuvertureCaisse.save(resultat);

        // Vérifier que l'item a été ajouté (génération d'un ID par la BDD)
        System.out.println("Résultat sauvegardé avec succès, ID généré : " + resultat.getId());

        return "{\"message\": \"Résultat enregistré avec succès\"}";
    }


    @RequestMapping(value = "/resulatCaisse", method = RequestMethod.GET)
    public String resultatCaisse(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String pseudo = authentication.getName();
            User user = userRepository.findByUsername(pseudo);
            model.addAttribute("user", user);
        }

        return "base";
    }

    @RequestMapping(value = "/objets-obtenus", method = RequestMethod.GET)
    public String afficherObjetsObtenus(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String pseudo = authentication.getName();
        User user = userRepository.findByUsername(pseudo);

        if (user == null) {
            return "redirect:/login";
        }

        List<ResultatOuvertureCaisse> objets = resultatOuvertureCaisse.findByUserId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("objets", objets);
        model.addAttribute("content", "resultatCaisse.html");

        return "base";
    }


    @PostMapping("/recuperer-objet")
    public String recupererObjet(@RequestParam("objetId") Long objetId, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String pseudo = authentication.getName();
        User user = userRepository.findByUsername(pseudo);
        if (user == null) {
            return "redirect:/login";
        }

        ResultatOuvertureCaisse objet = resultatOuvertureCaisse.findById(objetId).orElse(null);

        Inventaire inventaire = user.getPersonnage().getInventaire();


        CaseInventaire caseVide = null;
        for (CaseInventaire c : inventaire.getCaseInventaires()) {
            if (c.getType() == 0 && c.getTypeObjet() == 0 && c.getIdJeux() == 0) {
                caseVide = c;
                break; // Dès qu'on trouve une case vide, on s'arrête
            }
        }

        if (caseVide == null) {
            redirectAttributes.addFlashAttribute("error", "Votre inventaire est plein, l'objet ne peut pas être récuperer.");
            return "redirect:/objets-obtenus";
        }

        int type = -1;
        int typeObjet=-1;
        int idjeux=-1;
        switch (objet.getItemName()) {
            case "Épée":
                idjeux=1;
                typeObjet=1;
                type = 0;
                break;
            case "Bottes":
                idjeux=1;
                typeObjet=4;
                type = 0;
                break;
            case "Plastron":
                idjeux=1;
                typeObjet=3;
                type = 0;
                break;
            case "Casque":
                idjeux=1;
                typeObjet=2;
                type = 0;
                break;
            case "Gants":
                idjeux=1;
                typeObjet=5;
                type = 0;
                break;
            case "Potion":
                idjeux=1;
                typeObjet=6;
                type = 0;
                break;
            case "Potion de vitesse":
                idjeux=2;
                typeObjet=6;
                type = 0;
                break;

            default:
                System.out.println("Erreur : Objet introuvable");
                break;
        }

        caseVide.setIdJeux(idjeux);
        caseVide.setTypeObjet(typeObjet);
        caseVide.setType(type);
        caseInventaireRepository.save(caseVide);

        resultatOuvertureCaisse.delete(objet);

        redirectAttributes.addFlashAttribute("success", "Objet récupéré avec succès !");
        return "redirect:/objets-obtenus";
    }

    @PostMapping("/caisses/deduct-tokens")
    public ResponseEntity<?> deductTokens(@RequestBody Map<String, Integer> request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(403).body(Map.of("success", false, "message", "Utilisateur non authentifié"));
        }

        String pseudo = auth.getName();
        User user = userRepository.findByUsername(pseudo);
        if (user == null) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "Utilisateur introuvable"));
        }

        int tokensToDeduct = request.get("tokens");
        if (user.getTokens() < tokensToDeduct) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Tokens insuffisants"));
        }

        // Déduire les tokens et sauvegarder
        user.setTokens(user.getTokens() - tokensToDeduct);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("success", true, "newTokens", user.getTokens()));
    }



}
