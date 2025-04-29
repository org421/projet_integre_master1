package serveur.serveurjeux.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.UserRepository;


@Controller
public class CgController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/cgu", method = RequestMethod.GET)
    public String showCGUPage(Model m) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            m.addAttribute("user", user);
        }

        m.addAttribute("content", "cgu.html");
        return "base";
    }

    @RequestMapping(value = "/cgv", method = RequestMethod.GET)
    public String showCGVPage(Model m) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            m.addAttribute("user", user);
        }

        m.addAttribute("content", "cgv.html");
        return "base";
    }
}
