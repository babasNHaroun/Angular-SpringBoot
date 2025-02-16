package com.said.inotekk.crud_customers.services;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.said.inotekk.crud_customers.Exceptions.InsufficientStockException;
import com.said.inotekk.crud_customers.Exceptions.ProductNotFoundException;
import com.said.inotekk.crud_customers.entities.Order;
import com.said.inotekk.crud_customers.entities.Product;
import com.said.inotekk.crud_customers.helpUtil.OrderStatus;
import com.said.inotekk.crud_customers.repos.OrderRepository;
import com.said.inotekk.crud_customers.repos.ProductRepository;

import jakarta.annotation.PreDestroy;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private ExecutorService executorService;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public CompletableFuture<Order> createOrder(Order order) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                validateOrder(order);
                updateInventory(order);
                order.setOrderStatus(OrderStatus.PROCESSING);
                return orderRepository.save(order);
            } catch (InsufficientStockException ex) {
                order.setOrderStatus(OrderStatus.FAILED);
                orderRepository.save(order);
                throw ex;
            } catch (Exception ex) {
                order.setOrderStatus(OrderStatus.FAILED);
                orderRepository.save(order);
                throw ex;
            }
        }, executorService);

    }

    private void validateOrder(Order order) {
        order.getProducts().forEach(product -> {
            // existe t il deja
            Optional<Product> prod = productRepository.findById(product.getId());
            if (!prod.isPresent())
                throw new ProductNotFoundException(prod.get().getId());
            // check stock
            if (prod.get().getQuantity() <= 0) {
                throw new InsufficientStockException(prod.get().getId());
            }
        });
    }

    private void updateInventory(Order order) {
        order.getProducts().forEach(product -> {
            Optional<Product> currentProduct = productRepository.findById(product.getId());
            if (currentProduct.isPresent()) {
                final Product prod = currentProduct.get();
                prod.setQuantity(prod.getQuantity() - 1);
                productRepository.save(prod);
            }
        });
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }
}
