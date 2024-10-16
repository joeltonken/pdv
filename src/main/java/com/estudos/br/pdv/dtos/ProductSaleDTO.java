package com.estudos.br.pdv.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleDTO {

    @NotNull(message = "O item da venda é obrigatório")
    private long productId;

    @NotNull(message = "O campo quantidade é obrigatório")
    private int quantity;

}
