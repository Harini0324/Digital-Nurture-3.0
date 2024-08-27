package com.example.BookstoreAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    @JsonProperty("bookId")
    private Long id;

    @JsonProperty("bookTitle")
    private String title;

    private String author;
    private double price;
    private String isbn;
}
