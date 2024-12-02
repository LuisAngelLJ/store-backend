package com.la.store.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "El apellido no puede ser nulo")
    @Size(min = 1, max = 50, message = "El apellido debe tener entre 1 y 50 caracteres")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Número de teléfono inválido")
    @Column(name = "phone", nullable = true)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
