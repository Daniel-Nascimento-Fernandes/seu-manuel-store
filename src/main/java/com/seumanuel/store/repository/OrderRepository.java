package com.seumanuel.store.repository;

import com.seumanuel.store.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
//    Criar um novo pedido - save
//    Cancelar o pedido(lembrar da regra de negocio) - savez
//    Entregar o pedido - save
//    Confirmar o pedido - save
}
