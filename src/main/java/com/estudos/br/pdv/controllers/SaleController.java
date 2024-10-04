package com.estudos.br.pdv.controllers;

import com.estudos.br.pdv.dtos.ResponseDTO;
import com.estudos.br.pdv.dtos.SaleDTO;
import com.estudos.br.pdv.exceptions.InvalidOperationException;
import com.estudos.br.pdv.exceptions.NoItemFoundException;
import com.estudos.br.pdv.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity<>(saleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(saleService.getById(id), HttpStatus.OK);
        } catch (NoItemFoundException | InvalidOperationException e) {
            return new ResponseEntity<>(new ResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody SaleDTO saleDTO) {
        try {
            saleService.save(saleDTO);
            return new ResponseEntity<>(new ResponseDTO("Venda realizada com sucesso!"), HttpStatus.CREATED);
        } catch (NoItemFoundException | InvalidOperationException error) {
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception error) {
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
