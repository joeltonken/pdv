package com.estudos.br.pdv.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityConfig {

    public static PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
    }

}
