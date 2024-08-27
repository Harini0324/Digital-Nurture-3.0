package com.lib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

public class LibraryApplication {

    @SuppressWarnings("resource")
	public static void main(String[] args) {
        // Load the Spring context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the BookService bean using constructor injection
        @SuppressWarnings("unused")
		BookService bookServiceConstructor = (BookService) context.getBean("bookService");

        // Retrieve the BookService bean using setter injection
        @SuppressWarnings("unused")
		BookService bookServiceSetter = (BookService) context.getBean("bookServiceSetter");

        // Print messages to verify that both injections are working
        System.out.println("BookService bean with constructor injection is successfully wired with BookRepository.");
        System.out.println("BookService bean with setter injection is successfully wired with BookRepository.");

        // Optionally, you can add more logic to test BookService functionality
    }
}
