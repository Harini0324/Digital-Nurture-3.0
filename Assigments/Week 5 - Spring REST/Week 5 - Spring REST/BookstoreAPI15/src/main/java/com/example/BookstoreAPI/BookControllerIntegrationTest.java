package com.example.BookstoreAPI;

import com.example.BookstoreAPI.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        book.setAuthor("Author Name");
        book.setPrice(19.99);
        book.setIsbn("1234567890");
        bookRepository.save(book);
    }

    @Test
    void testGetBookById() throws Exception {
        mockMvc.perform(get("/books/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("Sample Book"))
               .andExpect(jsonPath("$.author").value("Author Name"));
    }

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].title").value("Sample Book"));
    }
}
