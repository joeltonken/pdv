package com.estudos.br.pdv.security;

import com.estudos.br.pdv.dtos.LoginDTO;
import com.estudos.br.pdv.entities.User;
import com.estudos.br.pdv.exceptions.PasswordNotFoundException;
import com.estudos.br.pdv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username);

        if (user == null)
            throw new UsernameNotFoundException("Login inválido!");

        return new UserPrincipal(user);
    }

    public void verifyUserCredentials(LoginDTO loginDTO) {
        UserDetails userDetails = loadUserByUsername(loginDTO.getUsername());
        boolean passwordIsTheSame = SecurityConfig.passwordEncoder().matches(loginDTO.getPassword(),
                userDetails.getPassword());

        if (!passwordIsTheSame)
            throw new PasswordNotFoundException("Senha inválida!");

    }

}
