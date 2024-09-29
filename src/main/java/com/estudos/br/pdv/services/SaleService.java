package com.estudos.br.pdv.services;

import com.estudos.br.pdv.dtos.ProductDTO;
import com.estudos.br.pdv.dtos.ProductInfoDTO;
import com.estudos.br.pdv.dtos.SaleDTO;
import com.estudos.br.pdv.dtos.SaleInfoDTO;
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
import java.time.format.DateTimeFormatter;
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
    public List<SaleInfoDTO> findAll() {
        return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }

    private SaleInfoDTO getSaleInfo(Sale sale) {
        SaleInfoDTO saleInfoDTO = new SaleInfoDTO();
        saleInfoDTO.setUser(sale.getUser().getName());
        saleInfoDTO.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        saleInfoDTO.setProducts(getProductInfo(sale.getItems()));

        return saleInfoDTO;
    }

    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {
        return items.stream().map(item -> {
            ProductInfoDTO productInfoDTO = new ProductInfoDTO();
            productInfoDTO.setDescription(item.getProduct().getDescription());
            productInfoDTO.setQuantity(item.getQuantity());
            return productInfoDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public SaleInfoDTO getById(long id) {
        Sale sale = saleRepository.findById(id).get();
        return getSaleInfo(sale);
    }

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
