package serveur.serveurjeux.Controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import serveur.serveurjeux.Entity.Article;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.ArticleRepository;
import serveur.serveurjeux.Repository.UserRepository;


@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    // loads the authenticated user's info
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String pageAccueil(Model m) throws MessagingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String pseudo = authentication.getName();
            User user = userRepository.findByUsername(pseudo);
            m.addAttribute("user", user);
        }


        Article lastPatch = articleRepository.findFirstByTypeOrderByDateDesc(Article.PATCH);
        Article lastActualite = articleRepository.findFirstByTypeOrderByDateDesc(Article.ACTUALITE);

        m.addAttribute("lastPatch", lastPatch);
        m.addAttribute("lastActualite", lastActualite);
        m.addAttribute("content", "accueil");
        return "base";
    }
}
