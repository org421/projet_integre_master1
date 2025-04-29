package serveur.serveurjeux.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import serveur.serveurjeux.Entity.PasswordResetToken;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.PasswordResetTokenRepository;
import serveur.serveurjeux.Repository.UserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;


    // registers a new user by encoding their password, assigning them the "ROLE_USER" role, and saving the user to the database
    public void registerUser(User user) {
        user.setMdp(passwordEncoder.encode(user.getMdp()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    // retrieves a user by email, returning null if the user is not enabled
    public User getUserByUsername(String pseudo) {
        User user = userRepository.findByUsername(pseudo);
        if (user != null && !user.isEnabled()) {
            return null;
        }
        return user;
    }

    // changes the user's password if the current password matches, updating it with the new encoded password and saving the user
    public boolean changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(currentPassword, user.getMdp())) {
            user.setMdp(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // verifies if the provided password matches the encoded password of the user
    public boolean verifPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getMdp());
    }

    // updates the user's personal information (name, email, first name, and phone) and saves the changes.
    public boolean updateUserInfo(User user, String email) {
        user.setEmail(email);
        userRepository.save(user);
        return true;
    }

    // updates the user's authentication context after changes to their account, re-authenticating the user with updated information
    public void updateConnexion(User user) {
        UserDetails userDetails = getUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }





    public boolean verifPseudoPasswordAndAccess(String pseudo, String password) {
        User user = getUserByUsername(pseudo);
        if (user != null){
            if(user.isEnabled() && passwordEncoder.matches(password, user.getMdp())) {
                return true;
            }
        }
        return false;
    }
}
