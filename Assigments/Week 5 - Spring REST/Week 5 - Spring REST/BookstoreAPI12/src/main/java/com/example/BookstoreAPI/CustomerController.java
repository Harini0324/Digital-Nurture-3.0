package com.example.BookstoreAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BookstoreAPI.assembler.CustomerResourceAssembler;
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

    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;

    // POST: Create a new customer with JSON request body
    @PostMapping
    public ResponseEntity<EntityModel<CustomerDTO>> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(customerResourceAssembler.toModel(customerMapper.toDTO(savedCustomer)), HttpStatus.CREATED);
    }

    // POST: Process form data for customer registration
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

    // GET: Retrieve all customers
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CustomerDTO>>> getAllCustomers() {
        List<EntityModel<CustomerDTO>> customerResources = customerRepository.findAll().stream()
                .map(customer -> customerResourceAssembler.toModel(customerMapper.toDTO(customer)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(customerResources));
    }

    // GET: Retrieve a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CustomerDTO>> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            return ResponseEntity.ok(customerResourceAssembler.toModel(customerMapper.toDTO(customer.get())));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // PUT: Update an existing customer
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

    // DELETE: Remove a customer by ID
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
