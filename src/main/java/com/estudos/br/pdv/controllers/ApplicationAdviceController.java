package com.estudos.br.pdv.controllers;

import com.estudos.br.pdv.dtos.ResponseDTO;
import com.estudos.br.pdv.exceptions.InvalidOperationException;
import com.estudos.br.pdv.exceptions.NoItemFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationAdviceController {

    @ExceptionHandler(NoItemFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleNoItemFoundException(NoItemFoundException ex) {
        String messageError = ex.getMessage();
        return new ResponseDTO(messageError);
    }

    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleInvalidOperationException(InvalidOperationException ex) {
        String messageError = ex.getMessage();
        return new ResponseDTO(messageError);
    }

}
