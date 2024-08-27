package com.example.BookstoreAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import com.example.BookstoreAPI.CustomerDTO;
import com.example.BookstoreAPI.mapper.CustomerMapper;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private List<Customer> customers = new ArrayList<>();

    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        customers.add(customer);
        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestParam Long id,
                                                        @RequestParam String name,
                                                        @RequestParam String email,
                                                        @RequestParam String address) {
        CustomerDTO customerDTO = new CustomerDTO(id, name, email, address);
        Customer customer = customerMapper.toEntity(customerDTO);
        customers.add(customer);
        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customers.stream()
            .map(customerMapper::toDTO)
            .toList();
    }
}

