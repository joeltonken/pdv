package com.estudos.br.pdv.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleInfoDTO {

    private String user;
    private String date;
    private List<ProductInfoDTO> products;

}
