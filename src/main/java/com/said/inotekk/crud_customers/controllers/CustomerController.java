package com.said.inotekk.crud_customers.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.said.inotekk.crud_customers.entities.Customer;
import com.said.inotekk.crud_customers.services.CustomerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/customer/{id}")
    public Customer putCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        return customerService.putCustomer(customer);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with Id:" + id));
    }

    @GetMapping("/customers/search")
    public List<Customer> searchCustomersByName(@RequestParam(name = "name", required = true) String name) {

        return customerService.getAllCustomersBySearchedName(name);
    }

    @GetMapping("/customers/search2")
    public List<Customer> searchCustomersByNameSQL(@RequestParam(name = "name", required = true) String name) {

        return customerService.getAllCustomersBySearchedNameSQL(name);
    }

    @GetMapping("customers/addAll")
    public List<Customer> addAllCustomers() {
        return customerService.initializeCustomers();
    }

    @GetMapping("customers/updateEmails")
    public void updateEmails() {
        customerService.updateAllEmails();
    }
}