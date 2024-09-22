package com.estudos.br.pdv.repositories;

import com.estudos.br.pdv.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
