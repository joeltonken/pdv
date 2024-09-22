package com.estudos.br.pdv.repositories;

import com.estudos.br.pdv.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
