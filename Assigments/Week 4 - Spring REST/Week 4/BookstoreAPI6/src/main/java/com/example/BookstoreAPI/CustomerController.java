package com.example.BookstoreAPI;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private List<Customer> customers = new ArrayList<>();

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        customers.add(customer);
        return customer;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public Customer registerCustomer(@RequestParam Long id,
                                     @RequestParam String name,
                                     @RequestParam String email,
                                     @RequestParam String address) {
        Customer customer = new Customer(id, name, email, address);
        customers.add(customer);
        return customer;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customers;
    }
}
