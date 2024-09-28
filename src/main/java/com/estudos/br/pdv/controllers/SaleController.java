package com.estudos.br.pdv.controllers;

import com.estudos.br.pdv.dtos.SaleDTO;
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
    public ResponseEntity getAll(@RequestBody SaleDTO saleDTO) {
        return new ResponseEntity<>(saleService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody SaleDTO saleDTO) {
        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity<>("Venda realizada com sucesso: " + id, HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
