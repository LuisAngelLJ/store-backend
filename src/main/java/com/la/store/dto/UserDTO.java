package com.la.store.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String lastName;
    private String phone;
    private AddressDTO address;
}
