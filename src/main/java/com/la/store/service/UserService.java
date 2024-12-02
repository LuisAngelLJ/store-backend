package com.la.store.service;

import com.la.store.dto.AddressDTO;
import com.la.store.dto.UserDTO;
import com.la.store.entity.Address;
import com.la.store.entity.User;
import com.la.store.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        // Asegúrate de que la dirección se establezca correctamente
        if (user.getAddress() != null) {
            Address address = user.getAddress();
            if (address.getCity() == null) {
                throw new IllegalArgumentException("City cannot be null");
            }
            // Resto de la lógica para guardar el usuario
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, UserDTO userDTO) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Actualizar los datos del usuario
            user.setName(userDTO.getName());
            user.setLastName(userDTO.getLastName());
            user.setPhone(userDTO.getPhone());

            // Actualizar la dirección
            Address address = user.getAddress();
            AddressDTO addressDTO = userDTO.getAddress();
            address.setStreet(addressDTO.getStreet());
            address.setCity(addressDTO.getCity());
            address.setZipCode(addressDTO.getZipCode());
            address.setState(addressDTO.getState());

            // Guardar el usuario, JPA hará el cascade en la dirección
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public Page<User> getAllUsers(Integer page, Integer size) {
        // Valores predeterminados si no se envían parámetros
        int pageNumber = (page == null || page < 0) ? 0 : page;
        int pageSize = (size == null || size <= 0) ? 10 : size; // Tamaño predeterminado: 10
        // crea un objeto de tipo Pageable, para la paginación pasando el número de página y el número de elementos por cada una
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return userRepository.findAll(pageable);
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("El usuario con ID " + id + " no existe.");
        }
    }

    public User getUser(Long idUser) {
        return userRepository.findById(idUser).orElse(null);
    }
}
