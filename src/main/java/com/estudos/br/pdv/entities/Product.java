package com.estudos.br.pdv.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_PRODUTO")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "O campo descrição é obrigatório")
    private String description;

    @Column(length = 20, precision = 20, scale = 2, nullable = false)
    @NotNull(message = "O campo preço é obrigatório")
    private BigDecimal price;

    @Column(nullable = false)
    @NotNull(message = "O campo quantidade é obrigatório")
    @Min(1)
    private int quantity;
}
