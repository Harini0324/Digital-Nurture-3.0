package com.example.BookstoreAPI;

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

    public BookController() {
        books.add(new Book(1L, "Spring in Action", "Craig Walls", 39.99, "9781617294945"));
        books.add(new Book(2L, "Clean Code", "Robert C. Martin", 29.99, "9780132350884"));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "HeaderValue");
        return new ResponseEntity<>(books, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();

        if (book.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "HeaderValue");
            return new ResponseEntity<>(book.get(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String title,
                                                   @RequestParam(required = false) String author) {
        List<Book> filteredBooks = books.stream()
                .filter(book -> (title == null || book.getTitle().equalsIgnoreCase(title)) &&
                                (author == null || book.getAuthor().equalsIgnoreCase(author)))
                .toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "HeaderValue");
        return new ResponseEntity<>(filteredBooks, headers, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        books.add(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "HeaderValue");
        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Optional<Book> existingBook = books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();

        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPrice(updatedBook.getPrice());
            book.setIsbn(updatedBook.getIsbn());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "HeaderValue");
            return new ResponseEntity<>(book, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
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
            return ResponseEntity.notFound().build();
        }
    }
}
