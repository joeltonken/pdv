package com.estudos.br.pdv.services;

import com.estudos.br.pdv.dtos.ProductDTO;
import com.estudos.br.pdv.dtos.SaleDTO;
import com.estudos.br.pdv.entities.ItemSale;
import com.estudos.br.pdv.entities.Product;
import com.estudos.br.pdv.entities.Sale;
import com.estudos.br.pdv.entities.User;
import com.estudos.br.pdv.repositories.ItemSaleRepository;
import com.estudos.br.pdv.repositories.ProductRepository;
import com.estudos.br.pdv.repositories.SaleRepository;
import com.estudos.br.pdv.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final UserRepository repository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;

    @Transactional
    public long save(SaleDTO dto) {

        User user = repository.findById(dto.getUserId()).get();

        Sale newSale = new Sale();
        newSale.setUser(user);
        newSale.setDate(LocalDate.now());

        List<ItemSale> items = getItemSale(dto.getItems());

        newSale = saleRepository.save(newSale);

        saveItemSale(items, newSale);

        return newSale.getId();
    }

    private void saveItemSale(List<ItemSale> items, Sale newSale) {

        for(ItemSale item: items){
            item.setSale(newSale);
            itemSaleRepository.save(item);
        }

    }

    private List<ItemSale> getItemSale(List<ProductDTO> products) {

        return products.stream().map(item -> {
            Product product = productRepository.getReferenceById(item.getProductId());

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            return itemSale;
        }).collect(Collectors.toList());

    }

}
