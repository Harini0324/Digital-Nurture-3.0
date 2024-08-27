package com.lib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

public class LibraryApplication {

    public static void main(String[] args) {
        // Load the Spring context
        @SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve and use a bean to trigger aspect advice
        BookService bookService = (BookService) context.getBean("bookService");
        
        // Example method call to test logging
        bookService.performService(); // Replace with an actual method call in BookService
        
        // Other application logic
    }
}
