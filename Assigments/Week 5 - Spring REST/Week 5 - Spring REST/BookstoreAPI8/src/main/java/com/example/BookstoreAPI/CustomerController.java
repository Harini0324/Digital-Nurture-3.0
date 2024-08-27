package com.example.BookstoreAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.BookstoreAPI.mapper.CustomerMapper;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(customerMapper.toDTO(savedCustomer), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestParam Long id,
                                                        @RequestParam String name,
                                                        @RequestParam String email,
                                                        @RequestParam String address) {
        CustomerDTO customerDTO = new CustomerDTO(id, name, email, address);
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(customerMapper.toDTO(savedCustomer), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customerDTOs = customerRepository.findAll().stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(customerDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customerMapper.toDTO(customer.get()), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerDTO customerDTO) {
        if (customerRepository.existsById(id)) {
            Customer customer = customerMapper.toEntity(customerDTO);
            customer.setId(id); // Ensure the ID is set for the update
            Customer updatedCustomer = customerRepository.save(customer);
            return new ResponseEntity<>(customerMapper.toDTO(updatedCustomer), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
