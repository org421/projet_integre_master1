package serveur.serveurjeux.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import serveur.serveurjeux.Entity.Category;
import serveur.serveurjeux.Entity.Reply;
import serveur.serveurjeux.Entity.Topic;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.CategoryRepository;
import serveur.serveurjeux.Repository.ReplyRepository;
import serveur.serveurjeux.Repository.TopicRepository;
import serveur.serveurjeux.Repository.UserRepository;
import serveur.serveurjeux.Service.ForumService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ForumController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ForumService forumService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @RequestMapping(value = "/forum", method = RequestMethod.GET)
    public String forumPage(@RequestParam(required = false) Long categoryId, Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            m.addAttribute("user", user);
        }

        // Gestion des grandes catégories et sous-catégories
        List<Map.Entry<String, List<Category>>> categoriesWithSubcategories = new ArrayList<>();
        Map<Integer, String> grandesCategories = Map.of(
                0, "Divers",
                1, "Problèmes techniques",
                2, "Guide"
        );

        List<Category> allCategories = categoryRepository.findAll();
        grandesCategories.forEach((key, value) -> {
            List<Category> filteredCategories = allCategories.stream()
                    .filter(category -> category.getNumCategorie() == key)
                    .toList();
            categoriesWithSubcategories.add(Map.entry(value, filteredCategories));
        });
        m.addAttribute("categoriesWithSubcategories", categoriesWithSubcategories);

        // Cas où une catégorie est sélectionnée
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Catégorie introuvable : " + categoryId));
            List<Topic> topics = topicRepository.findByCategoryId(categoryId);

            m.addAttribute("category", category); // Fournit la catégorie sélectionnée
            m.addAttribute("topics", topics);    // Fournit les sujets de cette catégorie
        }
        // Cas où aucune catégorie n'est sélectionnée
        else {
            List<Topic> topTopics = topicRepository.findTop3ByOrderByViewsDesc();
            List<Topic> mostRepliedTopics = topicRepository.findTop3ByMostReplies();

            m.addAttribute("category", null); // Aucune catégorie sélectionnée
            m.addAttribute("topTopics", topTopics); // Sujets les plus populaires
            m.addAttribute("mostRepliedTopics", mostRepliedTopics); // Sujets avec le plus de réponses
        }

        m.addAttribute("content", "forum");
        return "base";
    }




    @RequestMapping(value = "/forum/category", method = RequestMethod.GET)
    private String forumCategoryPage(@RequestParam Long id, Model m){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            m.addAttribute("user", user);
        }
        Category category = categoryRepository.findById(id).orElse(null);
        System.out.println("Category: " + category.getId());
        List<Topic> topics = topicRepository.findAllByCategory(category);

        m.addAttribute("topics", topics);
        m.addAttribute("category", category);

        m.addAttribute("content", "listTopicCategory.html");

        return "base";
    }

    @RequestMapping(value = "/forum/topic", method = RequestMethod.GET)
    public String topicPage(@RequestParam Long id, Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findByUsername(authentication.getName());
            m.addAttribute("user", user);
        }
        Topic topic = topicRepository.findById(id).orElseThrow(null);

        // Incrémenter le compteur de vues
        topic.setViews(topic.getViews() + 1);
        topicRepository.save(topic);

        m.addAttribute("topic", topic);
        m.addAttribute("replies", topic.getReplies());



        m.addAttribute("content", "Topic.html");

        return "base";
    }

    @RequestMapping(value = "/forum/topic/add-reply", method = RequestMethod.POST)
    public String addReply(@RequestParam Long topicId, @RequestParam String content, Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findByUsername(authentication.getName());
            m.addAttribute("user", user);
        }
        Topic topic = topicRepository.findById(topicId).orElse(null);

        User user = userRepository.findByUsername(authentication.getName());

        Reply reply = new Reply();
        reply.setAuthor(user);
        reply.setTopic(topic);
        reply.setCreatedAt(LocalDateTime.now());
        reply.setContent(content);
        topic.getReplies().add(reply);
        replyRepository.save(reply);
        topicRepository.save(topic);

        return "redirect:/forum/topic?id=" + topicId;
    }

}
