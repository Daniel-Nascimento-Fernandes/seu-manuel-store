package com.seumanuel.store.repository;

import com.seumanuel.store.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    //    Cadastrar - save
    //    Atualizar - save
    //    Excluir - delete
    //    Consultar pelo codigo - findById

    //    Consultar por parte do nome
    List<Product> findByNameContaining(String name);

    //    Consultar por preços dos produtos(preços entre um min e um max)
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);


}
