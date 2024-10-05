package com.estudos.br.pdv.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInfoDTO {

    private long productId;
    private String description;
    private int quantity;

}
