package serveur.serveurjeux.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import serveur.serveurjeux.Entity.Faq;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.FaqRepository;
import serveur.serveurjeux.Repository.UserRepository;

import java.util.List;

@Controller
public class FaqController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FaqRepository faqRepository;

    @RequestMapping(value = "/faq", method = RequestMethod.GET)
    public String faqPage(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            m.addAttribute("user", user);
        }

        List<Faq> faqs = faqRepository.findAll();
        m.addAttribute("faqs", faqs);
        m.addAttribute("content", "faq.html");
        return "base";

    }
}
