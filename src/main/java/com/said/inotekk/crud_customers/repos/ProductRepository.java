package com.said.inotekk.crud_customers.repos;

import org.springframework.data.repository.CrudRepository;

import com.said.inotekk.crud_customers.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
