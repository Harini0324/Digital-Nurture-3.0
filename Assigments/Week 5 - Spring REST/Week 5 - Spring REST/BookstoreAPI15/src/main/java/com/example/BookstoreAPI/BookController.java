package com.example.BookstoreAPI;

import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BookstoreAPI.assembler.BookResourceAssembler;
import com.example.BookstoreAPI.mapper.BookMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookResourceAssembler bookResourceAssembler;

    @Autowired
    private MeterRegistry meterRegistry; 

    private void incrementBookCountMetric() {
        meterRegistry.counter("custom_metric_total_books").increment();
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Retrieve all books", description = "Fetches a list of all books with content negotiation support")
    @ApiResponse(responseCode = "200", description = "List of books retrieved successfully")
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> getAllBooks() {
        List<EntityModel<BookDTO>> bookResources = bookRepository.findAll().stream()
                .map(book -> bookResourceAssembler.toModel(bookMapper.toDTO(book)))
                .collect(Collectors.toList());

        // Increment custom metric each time this endpoint is accessed
        incrementBookCountMetric();

        return ResponseEntity.ok(CollectionModel.of(bookResources));
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Retrieve a specific book by ID", description = "Fetches a book by its ID with content negotiation support")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<EntityModel<BookDTO>> getBookById(
            @Parameter(description = "ID of the book to retrieve") @PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            return ResponseEntity.ok(bookResourceAssembler.toModel(bookMapper.toDTO(book.get())));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/search", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Filter books based on query parameters", description = "Fetches books based on title and/or author")
    @ApiResponse(responseCode = "200", description = "Filtered list of books retrieved successfully")
    public ResponseEntity<List<BookDTO>> searchBooks(
            @Parameter(description = "Title of the book to search for") @RequestParam(required = false) String title,
            @Parameter(description = "Author of the book to search for") @RequestParam(required = false) String author) {
        List<BookDTO> filteredBooks = bookRepository.findAll().stream()
            .filter(book -> (title == null || book.getTitle().equalsIgnoreCase(title)) &&
                            (author == null || book.getAuthor().equalsIgnoreCase(author)))
            .map(bookMapper::toDTO)
            .toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "HeaderValue");

        // Example: Increment a different metric (if needed)
        meterRegistry.counter("custom_metric_filtered_books").increment();

        return new ResponseEntity<>(filteredBooks, headers, HttpStatus.OK);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Add a new book", description = "Adds a new book with content negotiation support")
    @ApiResponse(responseCode = "201", description = "Book created successfully")
    public ResponseEntity<BookDTO> addBook(
            @Parameter(description = "Book details to add") @RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookRepository.save(book);

        // Increment custom metric for new books added
        incrementBookCountMetric();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "HeaderValue");
        return new ResponseEntity<>(bookMapper.toDTO(savedBook), headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Update an existing book", description = "Updates a book by its ID with content negotiation support")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Book updated successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookDTO> updateBook(
            @Parameter(description = "ID of the book to update") @PathVariable Long id,
            @Parameter(description = "Updated book details") @RequestBody BookDTO bookDTO) {
        Optional<Book> existingBook = bookRepository.findById(id);

        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setPrice(bookDTO.getPrice());
            book.setIsbn(bookDTO.getIsbn());

            Book updatedBook = bookRepository.save(book);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "HeaderValue");
            return new ResponseEntity<>(bookMapper.toDTO(updatedBook), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .header("Custom-Header", "HeaderValue")
                                 .build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "Remove a book by ID", description = "Deletes a book by its ID with content negotiation support")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to delete") @PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);

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
