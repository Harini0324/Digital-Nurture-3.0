package com.example.BookstoreAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    @JsonProperty("customerId")
    private Long id;

    @JsonProperty("fullName")
    private String name;

    private String email;
    private String address;
}
