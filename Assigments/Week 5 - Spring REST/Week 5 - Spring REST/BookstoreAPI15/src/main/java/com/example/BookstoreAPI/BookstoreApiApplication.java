package com.example.BookstoreAPI;

import org.springframework.boot.SpringApplication;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;


@OpenAPIDefinition(
    info = @Info(
        title = "Online Bookstore API",
        version = "1.0",
        description = "API documentation for the Online Bookstore"
    )
)
public class BookstoreApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookstoreApiApplication.class, args);
    }
}