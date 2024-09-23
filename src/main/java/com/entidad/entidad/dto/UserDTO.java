package com.entidad.entidad.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    private Long id;
    private String nombre;
    private String username;
    private String password;
    private Long role; // Solo el nombre del rol o su ID
    private List<OrderDTO> orders;

}
