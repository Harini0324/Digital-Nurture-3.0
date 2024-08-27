package com.example.BookstoreAPI.assembler;

import com.example.BookstoreAPI.CustomerDTO;
import com.example.BookstoreAPI.CustomerController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomerResourceAssembler implements org.springframework.hateoas.RepresentationModelAssembler<CustomerDTO, EntityModel<CustomerDTO>> {

    @Override
    public EntityModel<CustomerDTO> toModel(CustomerDTO customerDTO) {
        EntityModel<CustomerDTO> customerResource = EntityModel.of(customerDTO);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(customerDTO.getId())).withSelfRel();
        Link allCustomersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers");
        customerResource.add(selfLink, allCustomersLink);

        return customerResource;
    }
}
