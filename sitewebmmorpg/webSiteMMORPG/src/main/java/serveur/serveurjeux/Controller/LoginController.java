package serveur.serveurjeux.Controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import serveur.serveurjeux.Entity.PasswordResetToken;
import serveur.serveurjeux.Entity.TokenValidation;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.PasswordResetTokenRepository;
import serveur.serveurjeux.Repository.TokenValidationRepository;
import serveur.serveurjeux.Repository.UserRepository;
import serveur.serveurjeux.Service.MailService;
import serveur.serveurjeux.Service.UserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenValidationRepository tokenValidationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // displays the login page
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model m, @RequestParam(value = "error", required = false) String error) {
        m.addAttribute("content", "login");
        if (error != null) {
            m.addAttribute("errorMessage", "Identifiants incorrects. Veuillez réessayer.");
        }
        return "base";
    }

    // displays the registration page
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model m,@RequestParam(value = "error", required = false) String error) {
        m.addAttribute("content", "register");
        return "base";
    }

    // registers a new user, creates a validation token and sends a validation email
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(@ModelAttribute User user, Model m, RedirectAttributes r) {
        try {
            userService.registerUser(user);
            String token = UUID.randomUUID().toString();
            TokenValidation validation = new TokenValidation();
            validation.setToken(token);
            validation.setUser(user);
            validation.setValid(false);
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.plusDays(2);
            validation.setExpirationDate(dateTime);
            tokenValidationRepository.save(validation);
            String validationUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/validationToken")
                    .queryParam("token", token)
                    .toUriString();
            Map<String,Object> option = new HashMap<>();
            option.put("url", validationUrl);
            mailService.sendMail(user.getEmail(), "MMORPG-VORTX validation de compte" , "emailValidationCompte", option);
            r.addFlashAttribute("successMessage", "Enregistrement réussi ! Vérfiez votre boite mail pour valider votre compte");
        } catch (Exception e) {
            r.addFlashAttribute("errorMessage", "Une erreur est survenue. Veuillez réessayer");
            return "redirect:/register";
        }

        return "redirect:/login";
    }

    // validates a user's token and activates their account
    @RequestMapping(value = "/validationToken", method = RequestMethod.GET)
    public String validationToken(@RequestParam("token") String token, Model m, RedirectAttributes r) {
        TokenValidation tokenToValide = tokenValidationRepository.findByToken(token);
        if(null != tokenToValide && !tokenToValide.getValid()) {
            tokenToValide.setValid(true);
            tokenValidationRepository.save(tokenToValide);
            User user = userRepository.findById(tokenToValide.getUser().getId()).orElse(null);
            if(user != null) {
                user.setEnabled(true);
                userRepository.save(user);

                m.addAttribute("valide", true);
                r.addFlashAttribute("successMessage", "Votre compte a été validé avec succès ! Vous pouvez maintenant vous connecter.");
            }
        } else {
            m.addAttribute("valide", false);
            r.addFlashAttribute("errorMessage2", "Le lien de validation est invalide ou a déjà été utilisé.");
        }
        m.addAttribute("content", "validationCompte");
        return "base";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public String processForgotPassword(@RequestParam("email2") String email, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = Optional.ofNullable(userRepository.findByEmail(email));

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = UUID.randomUUID().toString();

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setValid(true);
            resetToken.setExpirationDate(LocalDateTime.now().plusHours(1)); // Expiration dans 1 heure

            passwordResetTokenRepository.save(resetToken);

            String resetUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/reset-password")
                    .queryParam("token", token)
                    .toUriString();

            Map<String, Object> options = new HashMap<>();
            options.put("url", resetUrl);

            try {
                mailService.sendMail(user.getEmail(), "Réinitialisation de votre mot de passe", "emailResetPassword", options);
            } catch (MessagingException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'envoi de l'e-mail.");
                return "redirect:/forgot-password";
            }

            redirectAttributes.addFlashAttribute("successMessage", "Un email de réinitialisation a été envoyé.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Cet e-mail n'est pas enregistré.");
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.GET)
    public String showResetPasswordForm(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttributes) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);

        if (resetToken == null || !resetToken.getValid() || resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Le lien est invalide ou a expiré.");
            return "redirect:/login";
        }

        model.addAttribute("token", token);
        model.addAttribute("content", "resetPasswordForm");
        return "base";
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public String processResetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword, @RequestParam("newPassword1") String newPassword1, RedirectAttributes redirectAttributes) {

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken == null || !resetToken.getValid() || resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Le lien est invalide ou a expiré.");
            return "redirect:/login";
        }

        if (!newPassword.equals(newPassword1)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas. Veuillez réessayer.");
            return "redirect:/reset-password?token=" + token;
        }

        User user = resetToken.getUser();
        System.out.println("mail reset mdp : " + user.getEmail());
        user.setMdp(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setValid(false);
        passwordResetTokenRepository.save(resetToken);

        redirectAttributes.addFlashAttribute("successMessage", "Votre mot de passe a été réinitialisé avec succès !");
        return "redirect:/login";
    }



}
