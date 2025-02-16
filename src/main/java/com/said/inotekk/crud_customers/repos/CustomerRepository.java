package com.said.inotekk.crud_customers.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.said.inotekk.crud_customers.entities.Customer;

import jakarta.transaction.Transactional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByFirstNameContainingOrLastNameContainingIgnoreCase(@Param("name") String name);

    @Modifying
    @Query("UPDATE Customer c SET c.email = CONCAT('mail', c.email)")
    @Transactional
    public void updateAllEmails();

}
