package com.decagon.springsecurityjwt.security;

import com.decagon.springsecurityjwt.model.AppUser;
import com.decagon.springsecurityjwt.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is invoked when a request is made, and then the loadUserByUsername() method is being called
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    /**
     * @param email user email
     * @return <UserDetails>
     * @throws UsernameNotFoundException exception
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //TODO wrap in a try and catch block then handle exceptions properlly
        //check if the user exists
        Optional<AppUser> appUser = appUserRepository.findByEmail(email);
        //return the user if the user exists else throw an exception saying that that the user
        if(appUser.isPresent()) {
            return appUser.get();
        }
      throw new UsernameNotFoundException("Invalid Credentials"); //throw an exception here
    }
}
