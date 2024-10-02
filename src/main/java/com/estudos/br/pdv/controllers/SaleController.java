package com.estudos.br.pdv.controllers;

import com.estudos.br.pdv.dtos.ResponseDTO;
import com.estudos.br.pdv.dtos.SaleDTO;
import com.estudos.br.pdv.dtos.SaleInfoDTO;
import com.estudos.br.pdv.exceptions.InvalidOperationException;
import com.estudos.br.pdv.exceptions.NoItemFoundException;
import com.estudos.br.pdv.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity<>(new ResponseDTO<>("", saleService.findAll()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(new ResponseDTO<>("", saleService.getById(id)), HttpStatus.OK);
        } catch (NoItemFoundException | InvalidOperationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody SaleDTO saleDTO) {
        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity<>(new ResponseDTO<>("Venda realizada com sucesso!", id), HttpStatus.CREATED);
        } catch (NoItemFoundException | InvalidOperationException error) {
            return new ResponseEntity<>(new ResponseDTO<>(error.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception error) {
            return new ResponseEntity<>(new ResponseDTO<>(error.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
