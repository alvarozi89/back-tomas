package com.entidad.entidad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import com.entidad.entidad.model.User;
import com.entidad.entidad.model.Role;
import com.entidad.entidad.service.UserService;
import com.entidad.entidad.util.JwtTokenProvider;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://front-tomas.netlify.app")
public class AuthController {

    @Autowired
    private UserService empleadoService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {

            User empleado = empleadoService.validarCredenciales(loginRequest.getUsername(),
                    loginRequest.getPassword());

            if (empleado != null) {
                String role = empleado.getRole().getName();
                String token = tokenProvider.generateToken(empleado.getUsername(), role);
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                return ResponseEntity.status(401).body("Credenciales incorrectas");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Error en la autenticación");
        }
    }
}

class LoginRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
