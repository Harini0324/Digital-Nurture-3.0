package com.example.BookstoreAPI;

import io.micrometer.core.instrument.MeterRegistry;
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
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> getAllBooks() {
        List<EntityModel<BookDTO>> bookResources = bookRepository.findAll().stream()
                .map(book -> bookResourceAssembler.toModel(bookMapper.toDTO(book)))
                .collect(Collectors.toList());

        incrementBookCountMetric();

        return ResponseEntity.ok(CollectionModel.of(bookResources));
    }
    
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<EntityModel<BookDTO>> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            return ResponseEntity.ok(bookResourceAssembler.toModel(bookMapper.toDTO(book.get())));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping(value = "/search", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String author) {
        List<BookDTO> filteredBooks = bookRepository.findAll().stream()
            .filter(book -> (title == null || book.getTitle().equalsIgnoreCase(title)) &&
                            (author == null || book.getAuthor().equalsIgnoreCase(author)))
            .map(bookMapper::toDTO)
            .toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "HeaderValue");

        meterRegistry.counter("custom_metric_filtered_books").increment();

        return new ResponseEntity<>(filteredBooks, headers, HttpStatus.OK);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                 produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Book savedBook = bookRepository.save(book);

        incrementBookCountMetric();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "HeaderValue");
        return new ResponseEntity<>(bookMapper.toDTO(savedBook), headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
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
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
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
