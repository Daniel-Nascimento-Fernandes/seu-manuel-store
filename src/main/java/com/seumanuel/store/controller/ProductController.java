package com.seumanuel.store.controller;

import com.seumanuel.store.model.Product;
import com.seumanuel.store.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product save(@RequestBody Product product){
        return service.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable String id, @RequestBody Product product){
        return service.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable String id){
            return service.findById(id);
    }

    @GetMapping("/findByName")
    public List<Product> findByName(@RequestParam String name){
        return service.findByName(name);
    }

    @GetMapping("/findByPrice")
    public List<Product> findByPrice(@RequestParam BigDecimal min, @RequestParam BigDecimal max){
        return service.findByPrice(min,max);
    }

}
