package com.attendit.Attend.It.service.user;

import com.attendit.Attend.It.dao.UserRepository;
import com.attendit.Attend.It.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user entity from the database
        User user = userRepository.findByUsername(username);

        // Check if the user exists
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Return the UserDetails object
        return user; // Assuming User implements UserDetails or you convert it to UserDetails
    }

}
