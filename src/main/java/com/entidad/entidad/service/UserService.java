package com.entidad.entidad.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    public User saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El user no puede ser nulo");
        }
        if (!StringUtils.hasText(user.getNombre())) {
            throw new IllegalArgumentException("El nombre del user no puede estar vacío");
        }

        // Verifica si el nombre de usuario ya existe
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userActualizado) {
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
                    return userRepository.save(existingUser);
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
