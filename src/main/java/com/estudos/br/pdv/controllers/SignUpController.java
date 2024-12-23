package com.estudos.br.pdv.controllers;

import com.estudos.br.pdv.dtos.UserDTO;
import com.estudos.br.pdv.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-up")
public class SignUpController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody UserDTO user) {
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
