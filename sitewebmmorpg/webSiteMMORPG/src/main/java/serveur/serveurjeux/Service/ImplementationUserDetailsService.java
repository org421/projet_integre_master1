package serveur.serveurjeux.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.UserRepository;

@Service
public class ImplementationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    // Loads a user by their email for authentication, throwing an exception if not found, and returns a Spring Security UserDetails object
    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(pseudo);
        if (user == null) {
            System.out.println("ici2");
            throw new UsernameNotFoundException("User not found with username: " + pseudo);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getMdp(),
                user.isEnabled(),
                true,
                true,
                true,
                user.getAuthorities()
        );
    }
}
