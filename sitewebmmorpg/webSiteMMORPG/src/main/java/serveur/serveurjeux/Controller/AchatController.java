package serveur.serveurjeux.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import serveur.serveurjeux.Entity.Facture;
import serveur.serveurjeux.Entity.TokenPack;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.FactureRepository;
import serveur.serveurjeux.Repository.TokenRepository;
import serveur.serveurjeux.Repository.UserRepository;

import java.util.List;

@Controller
public class AchatController {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FactureRepository factureRepository;

    @RequestMapping(value = "/tokens", method = RequestMethod.GET)
    public String tokenPage(Model model) {
        List<TokenPack> tokenPacks = tokenRepository.findAll();
        model.addAttribute("tokenPacks", tokenPacks);
        model.addAttribute("content", "tokens.html");
        return "base";
    }


}
