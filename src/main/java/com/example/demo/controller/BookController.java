package com.example.demo.controller;

import com.example.demo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    Book book;

    /**
     * MappingJacksonToHttpMessageConverter
     */
    @GetMapping("")
    public Book book() {
        Book newBook = new Book();
        newBook.setName("三国演义");
        newBook.setAuthor("罗贯中");
        newBook.setPrice(129.99);
        newBook.setPublicationDate(new Date());
        return newBook;
    }

    @PostMapping("")
    public String addBook(@RequestBody String name) {
        return "{\"a\":" + name + "}";

    }

    @PostMapping("/{id}")
    public String echoId(@PathVariable Long id) {
        return String.valueOf(id);
    }
}
