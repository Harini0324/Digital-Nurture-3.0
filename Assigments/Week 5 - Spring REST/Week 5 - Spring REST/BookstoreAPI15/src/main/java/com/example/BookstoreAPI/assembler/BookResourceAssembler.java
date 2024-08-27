package com.example.BookstoreAPI.assembler;

import com.example.BookstoreAPI.BookDTO;

import com.example.BookstoreAPI.BookController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.RepresentationModelAssembler;

@Component
public class BookResourceAssembler implements RepresentationModelAssembler<BookDTO, EntityModel<BookDTO>> {

    @Override
    public EntityModel<BookDTO> toModel(BookDTO bookDTO) {
        EntityModel<BookDTO> bookResource = EntityModel.of(bookDTO);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDTO.getId())).withSelfRel();
        Link allBooksLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("all-books");
        bookResource.add(selfLink, allBooksLink);

        return bookResource;
    }
}
