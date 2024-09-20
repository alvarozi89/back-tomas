package com.entidad.entidad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.entidad.entidad.model.User;
import com.entidad.entidad.repository.UserRepository;

@Service
public class UDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User empleado = empleadoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado: " + username));
        return new UserPrincipal(empleado); // Clase que implementa UserDetails
    }
}
