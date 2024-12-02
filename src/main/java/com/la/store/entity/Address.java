package com.la.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "address")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La calle no puede ser nula")
    @Size(min = 1, max = 100, message = "La calle debe tener entre 1 y 100 caracteres")
    @Column(name = "street", nullable = false)
    private String street;

    @NotNull(message = "La ciudad no puede ser nula")
    @Size(min = 1, max = 50, message = "La ciudad debe tener entre 1 y 50 caracteres")
    @Column(name = "city", nullable = false)
    private String city;


    @Size(min = 1, max = 50, message = "El estado debe tener entre 1 y 50 caracteres")
    @Column(name = "state")
    private String state;

    @Pattern(regexp = "^[0-9]{5}$", message = "Código postal inválido")
    @Column(name = "zip_code")
    private String zipCode;
}
