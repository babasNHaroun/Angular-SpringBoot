package com.said.inotekk.crud_customers.repos;

import org.springframework.data.repository.CrudRepository;

import com.said.inotekk.crud_customers.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
