package com.estudos.br.pdv.controllers;

import com.estudos.br.pdv.dtos.LoginDTO;
import com.estudos.br.pdv.dtos.UserDTO;
import com.estudos.br.pdv.security.CustomUserDetailsService;
import com.estudos.br.pdv.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final CustomUserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody LoginDTO login) {
        try {
            userDetailsService.verifyUserCredentials(login);
            return null;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
