package serveur.serveurjeux.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import serveur.serveurjeux.Component.ConnexionServeurJeux;
import serveur.serveurjeux.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;

import serveur.serveurjeux.Repository.*;
import serveur.serveurjeux.Service.UserService;

import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    @Autowired
    private ConnexionServeurJeux connexionServeurJeux;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private CaseInventaireRepository caseInventaireRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String dashboard(Model m) throws MessagingException {

        m.addAttribute("content", "accueilAdmin");
        return "adminBase";
    }

    // loads all users, formats them as JSON, and returns the "adminBase" view
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/utilisateur", method = RequestMethod.GET)
    public String utilisateur(Model m, Authentication authentication,
                              @RequestParam(value = "role", required = false) String role) throws MessagingException {
        List<User> users;

        // Vérifie si l'utilisateur connecté est SUPER_ADMIN
        boolean isSuperAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SUPER_ADMIN"));

        // Récupérer les utilisateurs en fonction du rôle demandé
        if (role == null || role.equals("all")) {
            users = userRepository.findAll();
        } else if ("user".equalsIgnoreCase(role)) {
            users = userRepository.findByRole("ROLE_USER");
        } else if ("admin".equalsIgnoreCase(role)) {
            users = userRepository.findByRole("ROLE_ADMIN");
        } else {
            users = userRepository.findAll(); // Sécurité au cas où
        }

        // Exclure les super-admins de la liste (peu importe l'utilisateur connecté)
        users = users.stream()
                .filter(user -> !"ROLE_SUPER_ADMIN".equals(user.getRole()))
                .collect(Collectors.toList());

        // Transformer les utilisateurs en JSON
        List<Map<String, ? extends Serializable>> usersDto = users.stream()
                .map(user -> Map.of(
                        "id", user.getId(),
                        "nom", user.getUsername(),
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "enabled", user.isEnabled()
                ))
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            m.addAttribute("isSuperAdmin", isSuperAdmin);
            String usersJson = objectMapper.writeValueAsString(usersDto);
            m.addAttribute("users", usersJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        m.addAttribute("content", "utilisateurAdmin");
        return "adminBase";
    }


    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/changeUserRole", method = RequestMethod.POST)
    public String switchUserRole(@RequestParam("id") Long id) {
        System.out.println("Changement de rôle pour l'utilisateur ID: " + id);

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            if ("ROLE_ADMIN".equals(user.getRole())) {
                user.setRole("ROLE_USER");
                System.out.println("Utilisateur rétrogradé en USER");
            } else {
                user.setRole("ROLE_ADMIN");
                System.out.println("Utilisateur promu en ADMIN");
            }
            userRepository.save(user);
        } else {
            System.out.println("Utilisateur non trouvé !");
        }

        return "redirect:/admin/utilisateur";
    }




    // retrieves all tickets for users, formats them, and returns the "adminBase" view
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/support", method = RequestMethod.GET)
    public String support(Model m) throws MessagingException {
        // take all user
        List<User> users = userRepository.findAll();

        List<Map<String, Object>> ticketsDto = users.stream()
            .flatMap(user -> user.getTickets().stream()
                .map(ticket -> {
                    return Map.of(
                        "id", ticket.getId(),
                        "titre", ticket.getTitre(),
                        "description",ticket.getDescription(),
                        "status",ticket.getStatus(),
                        "priorite",ticket.getPrioriter(),
                        "client", (user.getUsername()),
                        "dateCreation",ticket.getDateCreation().toString(),
                        "dateModification", (Object) ticket.getDateModification() //object needed thx intelliJ for patch
                    );
                })
            )
            .collect(Collectors.toList());

        m.addAttribute("tickets", ticketsDto);
        m.addAttribute("content", "ticketAdmin");
        return "adminBase";
    }




    // toggles the status of a ticket between "open" and "closed"
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/ticketAdmin", method = RequestMethod.POST)
    public String switchOpenClose(@RequestParam("id") Long id){
        System.out.println("Arrivée ici ou las bas");
        Ticket ticket = ticketRepository.findById(id).get();
        if(ticket.getStatus()==ticket.OUVERT){
            ticket.setStatus(ticket.FERMER);
        } else {
            ticket.setStatus(ticket.OUVERT);
        }
        ticketRepository.save(ticket);
        return "redirect:/admin/support";
    }


    // toggles the "enabled" status of a user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/utilisateur", method = RequestMethod.POST)
    public String switchEnabeled(@RequestParam("id") Long id){

        User user = userRepository.findById(id).get();
        if(user.isEnabled()){
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        userRepository.save(user);
        return "redirect:/admin/utilisateur";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/list-article", method = RequestMethod.GET)
    public String listArticle(Model m) throws MessagingException {
        List<Article> articles = articleRepository.findAll();
        m.addAttribute("articles", articles);
        m.addAttribute("content", "listArticleAdmin.html");
        return "adminBase";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/nouvel-article", method = RequestMethod.GET)
    public String newArticle(Model m) throws MessagingException {

        m.addAttribute("content", "nouvelArticleAdmin.html");
        return "adminBase";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/createArticle", method = RequestMethod.POST)
    public String createArticle(@RequestParam("Titre") String title, @RequestParam("Description") String description,@RequestParam("typeArticle") int type,@RequestParam("content") String content) throws MessagingException {
        Article article = new Article();
        article.setTitle(title);
        article.setDescription(description);
        article.setType(type);
        article.setContent(content);
        LocalDateTime date = LocalDateTime.now();
        article.setDate(date);
        articleRepository.save(article);
        return "redirect:/admin/list-article";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/deleteArticle", method = RequestMethod.POST)
    public String deleteArticle(@RequestParam("idArticle") Long idArticle) throws MessagingException {
        Article article = articleRepository.findById(idArticle).get();
        articleRepository.delete(article);
        return "redirect:/admin/list-article";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/new-category", method = RequestMethod.GET)
    public String newCategory(Model m) throws MessagingException {

        m.addAttribute("content", "newCategoryAdmin.html");
        return "adminBase";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/list-categories", method = RequestMethod.GET)
    public String listCategory(Model m) throws MessagingException {
        List<Category> categories = categoryRepository.findAll();
        m.addAttribute("categories", categories);
        m.addAttribute("content", "listCategoryAdmin.html");
        return "adminBase";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @RequestMapping(value = "/admin/deleteCategory", method = RequestMethod.POST)
    public String deleteCategory(@RequestParam("idCategory") Long idCategory) throws MessagingException {

        topicRepository.deleteAllByCategoryId(idCategory);

        // Supprimer la catégorie
        categoryRepository.deleteById(idCategory);

        return "redirect:/admin/list-categories";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/createCategory", method = RequestMethod.POST)
    public String createCategory(@RequestParam("Name") String name, @RequestParam("Description") String description,@RequestParam("numCategorie") int numCategorie) throws MessagingException {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setNumCategorie(numCategorie);
        LocalDateTime date = LocalDateTime.now();
        categoryRepository.save(category);
        return "redirect:/admin/list-categories";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/list-topics", method = RequestMethod.GET)
    public String viewCategory(@RequestParam("idCategory") Long idCategory, Model model) {

        Category category = categoryRepository.findById(idCategory).orElseThrow(null);

        List<Topic> topics = topicRepository.findAllByCategory(category);

        model.addAttribute("topics", topics);
        model.addAttribute("category", category);
        model.addAttribute("content", "listTopicsAdmin.html");
        return "adminBase";
    }




    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/new-topic", method = RequestMethod.GET)
    public String newTopic(@RequestParam("idCategory") Long idCategory, Model model) {
        Category category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        model.addAttribute("category", category);
        model.addAttribute("content", "newTopicAdmin.html");
        return "adminBase";
    }




    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/createTopic", method = RequestMethod.POST)
    public String createTopic(@RequestParam("Titre") String title,@RequestParam("Contenue") String content,@RequestParam("idCategory") Long idCategory) {

        Category category = categoryRepository.findById(idCategory).orElseThrow(null);
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setCreatedAt(LocalDateTime.now());
        topic.setViews(0);
        topic.setCategory(category);
        category.getTopics().add(topic);
        topicRepository.save(topic);
        categoryRepository.save(category);
        return "redirect:/admin/list-topics?idCategory=" + category.getId();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/deleteTopic", method = RequestMethod.POST)
    public String deleteTopic(@RequestParam("idTopic") Long idTopic) throws MessagingException {
        Topic topic = topicRepository.findById(idTopic).orElseThrow(null);

        Category category = topic.getCategory();

        for(Reply reply : topic.getReplies()) {
            replyRepository.delete(reply);
        }
        topic.getReplies().clear();

         if (category != null) {
            category.getTopics().remove(topic);
            categoryRepository.save(category);
        }

        topicRepository.delete(topic);

        return "redirect:/admin/list-topics?idCategory=" + category.getId()     ;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/list-faq", method = RequestMethod.GET)
    public String listFaq(Model m) throws MessagingException {
        List<Faq> faqs = faqRepository.findAll();
        m.addAttribute("faqs", faqs);
        m.addAttribute("content", "listFaqAdmin.html");
        return "adminBase";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/createFaq", method = RequestMethod.POST)
    public String createFaq(@RequestParam("Question") String question, @RequestParam("Answer") String answer) {
        Faq faq = new Faq();
        faq.setQuestion(question);
        faq.setAnswer(answer);
        faqRepository.save(faq);

        return "redirect:/admin/list-faq";
    }


    @RequestMapping(value = "/admin/deleteFaq", method = RequestMethod.POST)
    public String deleteFaq(@RequestParam("idFaq") Long idFaq){
        Faq faq = faqRepository.findById(idFaq).orElseThrow(null);
        faqRepository.delete(faq);
        return "redirect:/admin/list-faq";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/server-status", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getServerStatus() {
        Map<String, Object> serverStatus = new HashMap<>();
        serverStatus.put("status", connexionServeurJeux.isServerOnline() ? "En ligne" : "Hors ligne");
        serverStatus.put("uptime", connexionServeurJeux.getUptime());


        return ResponseEntity.ok(serverStatus);
    }

    @Autowired
    private UserService userService;

    // Affiche le profil de l'admin avec ses informations
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "admin/profil", method = RequestMethod.GET)
    public String adminProfil(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String pseudo = authentication.getName();
        User admin = userService.getUserByUsername(pseudo);

        model.addAttribute("content", "admin-profil.html");
        model.addAttribute("admin", admin);

        return "adminBase";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "admin/change-password", method = RequestMethod.GET)
    public String changePassword(@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword, @RequestParam("confirmNewPassword") String confirmNewPassword, RedirectAttributes redirectAttributes, Principal principal) {
        String pseudo = principal.getName();
        User admin = userService.getUserByUsername(pseudo);
        boolean passwordChanged = userService.changePassword(admin.getEmail(), currentPassword, newPassword);

        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("passwordError", "Les nouveaux mots de passe ne correspondent pas.");
            return "redirect:/admin/profil?section=changePassword";
        }

        if (!passwordChanged) {
            redirectAttributes.addFlashAttribute("passwordError", "Le mot de passe actuel est incorrect.");
        } else {
            redirectAttributes.addFlashAttribute("passwordSuccess", "Votre mot de passe a été modifié avec succès.");
        }

        return "redirect:/admin/profil?section=changePassword";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "admin/changeInventaire", method = RequestMethod.GET)
    public String changeInventaireInfo(Model model) {
        model.addAttribute("users", userRepository.findByPersonnageIsNotNull());
        model.addAttribute("content", "adminChangerInventaire.html");
        return "adminBase";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "admin/saveInventaire", method = RequestMethod.POST)
    public String saveInventaireInfo(@RequestParam("idCase") Long id,@RequestParam("idJeu") int idJeu, @RequestParam("typeObjet") int typeObjet, @RequestParam("type") int type ) {
        if(caseInventaireRepository.findById(id).isPresent()){
            CaseInventaire caseInventaire = caseInventaireRepository.findById(id).get();
            caseInventaire.idJeux = idJeu;
            caseInventaire.typeObjet = typeObjet;
            caseInventaire.type = type;
            caseInventaireRepository.save(caseInventaire);
        }

        return "redirect:/admin/changeInventaire";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "admin/update-info", method = RequestMethod.GET)
    public String updateAdminInfo(@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes redirectAttributes, Principal principal) {
        String pseudo = principal.getName();
        User admin = userService.getUserByUsername(pseudo);

        // Vérification du mot de passe
        if (!userService.verifPassword(admin, password)) {
            redirectAttributes.addFlashAttribute("updateError", "Mot de passe incorrect.");
            return "redirect:/admin/profil?section=editInfo";
        }

        // Mise à jour des informations
        boolean updateSuccess = userService.updateUserInfo(admin, email);
        if (updateSuccess) {
            redirectAttributes.addFlashAttribute("updateSuccess", "Informations modifiées avec succès.");
        } else {
            redirectAttributes.addFlashAttribute("updateError", "Erreur lors de la mise à jour des informations.");
        }

        return "redirect:/admin/profil?section=editInfo";
    }



}
