package com.entidad.entidad.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.entidad.entidad.dto.OrderDTO;
import com.entidad.entidad.dto.OrdersDTO;
import com.entidad.entidad.dto.UserDTO;
import com.entidad.entidad.model.Orders;
import com.entidad.entidad.model.Product;
import com.entidad.entidad.model.User;
import com.entidad.entidad.repository.OrderRepository;
import com.entidad.entidad.repository.ProductRepository;
import com.entidad.entidad.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrdersDTO saveOrder(OrdersDTO orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        Orders order = new Orders();
        order.setQuantity(orderDTO.getQuantity());
        order.setTotalPrice(orderDTO.getTotalPrice());

        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + orderDTO.getUserId()));
        order.setUser(user);

        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Product not found with ID: " + orderDTO.getProductId()));
        order.setProduct(product);

        Orders savedOrder = orderRepository.save(order);

        return new OrdersDTO(
                savedOrder.getId(),
                savedOrder.getUser().getId(),
                savedOrder.getProduct().getId(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice());
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

    public List<UserDTO> getTop5FrequentCustomers() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Object[]> results = orderRepository.findTopFrequentCustomers(pageable);
        return results.stream()
                .map(result -> {
                    Long userId = (Long) result[0];
                    if (userId != null) {
                        User user = userRepository.findById(userId).orElse(null);
                        if (user != null) {
                            // Mapear User a UserDTO
                            UserDTO userDTO = new UserDTO();
                            userDTO.setId(user.getId());
                            userDTO.setNombre(user.getNombre());
                            userDTO.setUsername(user.getUsername());
                            userDTO.setRole(user.getRole().getId());
                            userDTO.setOrders(
                                    user.getOrders().stream().map(this::mapToOrderDTO).collect(Collectors.toList()));
                            return userDTO;
                        }
                    }
                    return null;
                })
                .filter(userDTO -> userDTO != null) // Filtrar usuarios nulos
                .collect(Collectors.toList());
    }

    private OrderDTO mapToOrderDTO(Orders order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setProductName(order.getProduct().getName());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setTotalPrice(order.getTotalPrice());
        return orderDTO;
    }

}
