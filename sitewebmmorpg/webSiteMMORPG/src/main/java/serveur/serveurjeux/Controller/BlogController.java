package serveur.serveurjeux.Controller;


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

import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public String blogPage(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String pseudo = authentication.getName();
            User user = userRepository.findByUsername(pseudo);
            m.addAttribute("user", user);
        }

        List<Article> articles = articleRepository.findAllByType(Article.BLOG);
        m.addAttribute("articles", articles);
        m.addAttribute("content", "blog");

        return "base";
    }
}
