package com.entidad.entidad.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entidad.entidad.model.Product;
import com.entidad.entidad.model.User;
import com.entidad.entidad.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getAll")
    public List<User> getAllusers() {
        return userService.getAllUsers();
    }

    @GetMapping("getName")
    public ResponseEntity<List<User>> searchByKeyword(@RequestParam String keyword) {
        List<User> users = userService.searchByKeyword(keyword);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public Optional<User> getuserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createuser(@RequestBody User user) {
        User nuevoUser = userService.saveUser(user);
        return ResponseEntity.ok(nuevoUser);
    }

    @PutMapping("/user/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userActualizado) {
        return userService.updateUser(id, userActualizado);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Void> deleteuser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}