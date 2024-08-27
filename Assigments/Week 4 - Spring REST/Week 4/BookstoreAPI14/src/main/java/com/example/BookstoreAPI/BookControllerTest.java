package com.example.BookstoreAPI;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.BookstoreAPI.BookDTO;
import com.example.BookstoreAPI.mapper.BookMapper;
import com.example.BookstoreAPI.Book;
import com.example.BookstoreAPI.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@WebMvcTest(BookController.class)
public class BookControllerTest<BookRepository> {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookMapper bookMapper;

    @Test
    void testGetBookById() throws Exception {
        Book sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Sample Book");
        sampleBook.setAuthor("Author Name");
        sampleBook.setPrice(19.99);
        sampleBook.setIsbn("1234567890");

        BookDTO sampleBookDTO = new BookDTO();
        sampleBookDTO.setId(1L);
        sampleBookDTO.setTitle("Sample Book");
        sampleBookDTO.setAuthor("Author Name");
        sampleBookDTO.setPrice(19.99);
        sampleBookDTO.setIsbn("1234567890");

        when(((CrudRepository<Book, Long>) bookRepository).findById(1L)).thenReturn(Optional.of(sampleBook));
        when(bookMapper.toDTO(sampleBook)).thenReturn(sampleBookDTO);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Book"))
                .andExpect(jsonPath("$.author").value("Author Name"))
                .andExpect(jsonPath("$.price").value(19.99))
                .andExpect(jsonPath("$.isbn").value("1234567890"));
    }
}
