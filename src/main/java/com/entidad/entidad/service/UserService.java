package com.entidad.entidad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.entidad.entidad.dto.UserDTO;
import com.entidad.entidad.model.Role;
import com.entidad.entidad.model.User;
import com.entidad.entidad.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del user no es válido");
        }
        return userRepository.findById(id);
    }

    public UserDTO saveUser(UserDTO userDTO) {

        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("El usuario con el nombre de usuario " + userDTO.getUsername() + " ya existe.");
        }

        User user = new User();
        user.setNombre(userDTO.getNombre());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role = new Role();
        role.setId(userDTO.getRole());
        user.setRole(role);

        User nuevoUser = userRepository.save(user);
        UserDTO responseDTO = new UserDTO();
        responseDTO.setId(nuevoUser.getId());
        responseDTO.setNombre(nuevoUser.getNombre());
        responseDTO.setUsername(nuevoUser.getUsername());
        responseDTO.setRole(nuevoUser.getRole().getId());
        responseDTO.setPassword(nuevoUser.getPassword());
        return responseDTO;
    }

    public UserDTO updateUser(Long id, UserDTO userActualizado) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Actualizar solo los campos que se proporcionan
                    if (userActualizado.getNombre() != null) {
                        existingUser.setNombre(userActualizado.getNombre());
                    }
                    if (userActualizado.getUsername() != null) {
                        existingUser.setUsername(userActualizado.getUsername());
                    }
                    // Añadir más campos según sea necesario
                    User updatedUser = userRepository.save(existingUser);
                    UserDTO responseDTO = new UserDTO();
                    responseDTO.setNombre(updatedUser.getNombre());
                    responseDTO.setUsername(updatedUser.getUsername());
                    // Añadir más campos según sea necesario
                    return responseDTO;
                })
                .orElseThrow(() -> new RuntimeException("Usuario not found with id " + id));
    }

    public void deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del usuario no es válido");
        }
        userRepository.deleteById(id);
    }

    public User validarCredenciales(String username, String password) {
        Optional<User> useroOpt = userRepository.findByUsername(username);
        if (useroOpt.isPresent()) {
            User user = useroOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new RuntimeException("No se encontró el usuario con nombre de usuario " + username);
        }
        return user;
    }

    public List<User> searchByKeyword(String keyword) {
        List<User> user = userRepository.searchByKeyword(keyword);
        if (user.isEmpty()) {
            throw new RuntimeException("No se encontraron usuarios con la palabra clave " + keyword);
        }
        return user;
    }
}
