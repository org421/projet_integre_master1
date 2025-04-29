package serveur.serveurjeux.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import serveur.serveurjeux.Entity.Article;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.ArticleRepository;
import serveur.serveurjeux.Repository.UserRepository;

import java.util.List;

@Controller
public class WikiController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(value = "/wiki", method = RequestMethod.GET)
    public String wikiPage(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String pseudo = authentication.getName();
            User user = userRepository.findByUsername(pseudo);
            m.addAttribute("user", user);
        }
        List<Article> articles = articleRepository.findAllByType(3);
        m.addAttribute("articles", articles);
        m.addAttribute("content","wiki");
        return "base";
    }

    @GetMapping("/articles")
    public Page<Article> getWikiArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String search) {

        Pageable pageable = PageRequest.of(page, size);
        return search.isEmpty()
                ? articleRepository.findByType(3, pageable)
                : articleRepository.findByTypeAndTitleContaining(3, search, pageable);
    }
}
