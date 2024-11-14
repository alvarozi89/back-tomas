package com.entidad.entidad.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    private Long id;
    private Long userId; // ID del usuario
    private Long productId; // ID del producto
    private Integer quantity;
    private Double totalPrice;
}