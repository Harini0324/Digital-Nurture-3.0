package com.example.BookstoreAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    @JsonProperty("bookId")
    private Long id;

    @JsonProperty("bookTitle")
    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotNull(message = "Author cannot be null")
    private String author;

    @Min(value = 0, message = "Price must be positive")
    private double price;

    private String isbn;
}
