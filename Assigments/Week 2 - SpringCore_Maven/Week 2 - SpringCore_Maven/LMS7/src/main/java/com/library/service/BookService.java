package com.library.service;

import com.library.repository.BookRepository;

public class BookService {

    private BookRepository bookRepository;

    // Setter method for BookRepository
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public BookService() {
    }
    
    public void performService() {
        System.out.println("Book service is now operational.");
        bookRepository.performRepositoryOperation();
    }
}