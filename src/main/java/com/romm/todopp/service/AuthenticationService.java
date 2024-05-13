package com.romm.todopp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.romm.todopp.DTO.RegistrationDTO;
import com.romm.todopp.entity.User;
import com.romm.todopp.exceptions.UsernameAlreadyExistsException;
import com.romm.todopp.repository.UserRepository;

@Service
public class AuthenticationService {
    
    @Autowired UserRepository userRepository;

    //@Autowired UserService userService;

    @Autowired BCryptPasswordEncoder passwordEncoder;

    public User register(RegistrationDTO data) {
        boolean existsAnUserWithThatUsername = !userRepository.findByUsername(data.username()).isEmpty();
        if (existsAnUserWithThatUsername)
            throw new UsernameAlreadyExistsException("there is an user with this username.", null);

        User user = new User();

        String password = passwordEncoder.encode(data.password());

        user.setUsername(data.username());
        user.setEmail(data.email());
        user.setPassword(password);

        return userRepository.save(user);
    }

    // public UserDetails login(LoginDTO data) throws UsernameNotFoundException {
    //     UserDetails principal = userService.loadUserByUsername(data.username());

        
    //     if (!passwordEncoder.matches(data.password(), principal.getPassword()))
    //         return null;

    //     Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
    //     SecurityContext context = SecurityContextHolder.getContext();
    //     context.setAuthentication(authentication);
    //     System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA\n aaaaaaaaaaaaaaaaaaaaaaaaaaaa\n aaaaaaaaaaaaaaaaaa \n AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    //     return principal;
    // }

    public User getPrincipal() {
        var principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findById(principal.getId()).get();
        return user;
    }
}
