package com.said.inotekk.crud_customers.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.said.inotekk.crud_customers.entities.Product;
import com.said.inotekk.crud_customers.repos.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        final List<Product> list = new ArrayList<>();
        productRepository.findAll().forEach(list::add);
        return list;
    }
}
