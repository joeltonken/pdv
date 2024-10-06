package com.estudos.br.pdv.services;

import com.estudos.br.pdv.dtos.ProductSaleDTO;
import com.estudos.br.pdv.dtos.ProductInfoDTO;
import com.estudos.br.pdv.dtos.SaleDTO;
import com.estudos.br.pdv.dtos.SaleInfoDTO;
import com.estudos.br.pdv.entities.ItemSale;
import com.estudos.br.pdv.entities.Product;
import com.estudos.br.pdv.entities.Sale;
import com.estudos.br.pdv.entities.User;
import com.estudos.br.pdv.exceptions.InvalidOperationException;
import com.estudos.br.pdv.exceptions.NoItemFoundException;
import com.estudos.br.pdv.repositories.ItemSaleRepository;
import com.estudos.br.pdv.repositories.ProductRepository;
import com.estudos.br.pdv.repositories.SaleRepository;
import com.estudos.br.pdv.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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

        return SaleInfoDTO.builder().
                user(sale.getUser().getName())
                .date(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .products(getProductInfo(sale.getItems()))
                .build();
    }

    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {

        if (CollectionUtils.isEmpty(items))
            return Collections.emptyList();

        return items.stream().map(
                item -> ProductInfoDTO.builder()
                        .productId(item.getId())
                        .description(item.getProduct().getDescription())
                        .quantity(item.getQuantity())
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public SaleInfoDTO getById(long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new NoItemFoundException("Venda não encontrada."));

        return getSaleInfo(sale);
    }

    @Transactional
    public long save(SaleDTO dto) {
        User user = repository.findById(dto.getUserId())
                .orElseThrow(() -> new NoItemFoundException("Usuário não encontrado."));

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

    private List<ItemSale> getItemSale(List<ProductSaleDTO> products) {

        if (products.isEmpty())
            throw new InvalidOperationException("Não é possível adicionar a venda sem itens");

        return products.stream().map(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new NoItemFoundException("Item da venda não encontrado."));

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            if (product.getQuantity() == 0)
                throw new NoItemFoundException("Produto sem estoque.");
            else if (product.getQuantity() < item.getQuantity())
                throw new InvalidOperationException(
                        String.format("A quantidade de itens de venda {%s} " +
                        "é maior do que a quantidade disponível no estoque {%s}", item.getQuantity(), product.getQuantity()));

            int total = product.getQuantity() - item.getQuantity();
            product.setQuantity(total);
            productRepository.save(product);

            return itemSale;
        }).collect(Collectors.toList());

    }

}
