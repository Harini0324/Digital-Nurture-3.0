package com.example.BookstoreAPI;

import com.example.BookstoreAPI.CustomerDTO;
import com.example.BookstoreAPI.mapper.BookMapper;
import com.example.BookstoreAPI.mapper.CustomerMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> books = new ArrayList<>();

    @Autowired
    private BookMapper bookMapper;

    public BookController() {
        books.add(new Book(1L, "Spring in Action", "Craig Walls", 39.99, "9781617294945"));
        books.add(new Book(2L, "Clean Code", "Robert C. Martin", 29.99, "9780132350884"));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> bookDTOs = books.stream()
            .map(bookMapper::toDTO)
            .toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "HeaderValue");
        return new ResponseEntity<>(bookDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> book = books.stream()
            .filter(b -> b.getId().equals(id))
            .findFirst();

        if (book.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "HeaderValue");
            return new ResponseEntity<>(bookMapper.toDTO(book.get()), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .header("Custom-Header", "HeaderValue")
                                 .build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String author) {
        List<BookDTO> filteredBooks = books.stream()
            .filter(book -> (title == null || book.getTitle().equalsIgnoreCase(title)) &&
                            (author == null || book.getAuthor().equalsIgnoreCase(author)))
            .map(bookMapper::toDTO)
            .toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "HeaderValue");
        return new ResponseEntity<>(filteredBooks, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        books.add(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "HeaderValue");
        return new ResponseEntity<>(bookDTO, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Optional<Book> existingBook = books.stream()
            .filter(book -> book.getId().equals(id))
            .findFirst();

        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setPrice(bookDTO.getPrice());
            book.setIsbn(bookDTO.getIsbn());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "HeaderValue");
            return new ResponseEntity<>(bookDTO, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .header("Custom-Header", "HeaderValue")
                                 .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        
        if (removed) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "HeaderValue");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .header("Custom-Header", "HeaderValue")
                                 .build();
        }
    }
}
