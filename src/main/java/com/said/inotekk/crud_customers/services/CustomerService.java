package com.said.inotekk.crud_customers.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.said.inotekk.crud_customers.entities.Customer;
import com.said.inotekk.crud_customers.repos.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            customers.add(customer);
        }

        return customers;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer putCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomersBySearchedName(String name) {

        long start = System.currentTimeMillis();

        List<Customer> list = new ArrayList<Customer>();
        final Iterable<Customer> allCustomers = customerRepository.findAll();
        allCustomers.forEach(list::add);
        list = list.stream()
                .filter(
                        customer -> customer.getFirstName().toLowerCase().contains(name.toLowerCase())
                                || customer.getLastName().toLowerCase().contains(name
                                        .toLowerCase()))
                .collect(Collectors.toList());

        long finish = System.currentTimeMillis();
        System.out.println("Time taken : " + (finish - start));
        return list;
    }

    public List<Customer> getAllCustomersBySearchedNameSQL(String name) {
        long start = System.currentTimeMillis();
        List<Customer> list = customerRepository.findByFirstNameContainingOrLastNameContainingIgnoreCase(name);
        long finish = System.currentTimeMillis();
        System.out.println("Time taken : " + (finish - start));
        return list;
    }

    public List<Customer> initializeCustomers() {
        List<Customer> list = new ArrayList<Customer>();
        int i = 100;
        while (i > 0) {
            Customer customer = new Customer();
            customer.setFirstName(UUID.randomUUID().toString().substring(0, 8));
            customer.setLastName(UUID.randomUUID().toString().substring(0, 8));
            customer.setEmail("user" + i + "@gmail.com");
            customer.setPhone("+1" + String.format("%010d", new Random().nextInt(1000000000)));
            list.add(customer);
            i--;
        }

        customerRepository.saveAll(list).forEach(list::add);
        return list;

    }

    public void updateAllEmails() {
        customerRepository.updateAllEmails();
    }

}
