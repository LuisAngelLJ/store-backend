package com.la.store.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String street;
    private String city;
    private String zipCode;
    private String state;
}
