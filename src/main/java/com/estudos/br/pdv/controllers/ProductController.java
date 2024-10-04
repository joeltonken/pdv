package com.estudos.br.pdv.controllers;

import com.estudos.br.pdv.dtos.ResponseDTO;
import com.estudos.br.pdv.entities.Product;
import com.estudos.br.pdv.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity put(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO("Produto removido com sucesso."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
