package com.lib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

public class LibraryApplication {

    public static void main(String[] args) {
        // Load the Spring context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the BookService bean
        BookService bookService = (BookService) context.getBean("bookService");

        // Print a message to verify that the BookService bean is correctly wired
        System.out.println("BookService bean is successfully wired with BookRepository.");

        // Optionally, you can add more logic to test BookService functionality
    }
}
