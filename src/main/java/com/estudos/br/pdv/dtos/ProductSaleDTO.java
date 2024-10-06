package com.estudos.br.pdv.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleDTO {

    private long productId;
    private int quantity;

}
