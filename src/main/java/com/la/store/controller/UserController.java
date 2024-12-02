package com.la.store.controller;

import com.la.store.dto.UserDTO;
import com.la.store.entity.Address;
import com.la.store.entity.User;
import com.la.store.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());

        Address address = new Address();
        address.setStreet(userDTO.getAddress().getStreet());
        address.setCity(userDTO.getAddress().getCity()); // Asegúrate de que esto se está asignando
        address.setZipCode(userDTO.getAddress().getZipCode());
        address.setState(userDTO.getAddress().getState());
        user.setAddress(address);

        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(userId, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/list")
    public Page<User> listUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,  // Opcional, predeterminado a 0
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size   // Opcional, predeterminado a 10
    ) {
        return userService.getAllUsers(page, size);
    }
    /*
    Obtener la segunda página con 5 elementos por página: http://localhost:8080/api/users/list?page=1&size=5
    Obtener la primera página con 20 elementos por página: http://localhost:8080/api/users/list?page=0&size=20
    */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();  // Devuelve 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).build();  // Devuelve 404 si no se encuentra
        } catch (Exception e) {
            return ResponseEntity.status(500).build();  // Devuelve 500 para otros errores
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getUser(id));
    }
}
