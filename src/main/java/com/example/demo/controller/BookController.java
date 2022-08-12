package com.example.demo.controller;

import com.example.demo.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class BookController {
    @Autowired
    Book book;

    /**
     * MappingJacksonToHttpMessageConverter
     *
     */
    @GetMapping("/book")
    public Book book() {
        Book newBook = new Book();
        newBook.setName("三国演义");
        newBook.setAuthor("罗贯中");
        newBook.setPrice(129.99);
        newBook.setPublicationDate(new Date());
        return newBook;
    }
}
