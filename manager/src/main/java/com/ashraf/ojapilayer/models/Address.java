package com.ashraf.ojapilayer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String city;

    private String state;

    private String country;

    private String pinCode;

}
