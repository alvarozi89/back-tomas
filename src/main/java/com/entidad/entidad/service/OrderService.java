package com.entidad.entidad.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.entidad.entidad.model.Orders;
import com.entidad.entidad.model.User;
import com.entidad.entidad.repository.OrderRepository;
import com.entidad.entidad.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders saveOrder(Orders order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        return orderRepository.save(order);
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La orden con el ID especificado no existe"));
    }

    public Orders updateOrder(Long id, Orders order) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("La orden con el ID especificado no existe");
        }
        order.setId(id);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("La orden con el ID especificado no existe");
        }
        orderRepository.deleteById(id);
    }

    public List<User> getTop5FrequentCustomers() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Object[]> results = orderRepository.findTopFrequentCustomers(pageable);

        List<Long> userIds = results.stream()
                .map(result -> (Long) result[0])
                .collect(Collectors.toList());

        return userRepository.findAllById(userIds);
    }

}
