package com.seumanuel.store.service;

import com.seumanuel.store.model.Product;
import com.seumanuel.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product findById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public Product update(String id, BigDecimal stock) {

        // get objeto
        Optional opt = repository.findById(id);
        if(!opt.isPresent())
            throw new RuntimeException("No order found");

        Product product = (Product) opt.get();

        // alterar estado
        product.setStock(stock);

        return repository.save(product);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<Product> findByName(String name){
        return repository.findByNameContaining(name);
    }

    public List<Product> findByPrice(BigDecimal min, BigDecimal max){
        return repository.findByPriceBetween(min, max);
    }
}
