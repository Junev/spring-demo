package com.example.demo.controller;

import com.example.demo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    Book book;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

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

    @PostMapping("/addInRedis")
    public void addInRedis() {
        ValueOperations<String, String> operations =
                stringRedisTemplate.opsForValue();
        operations.set("name", "三国演艺");
        String name = operations.get("name");
        System.out.println(name);

        ValueOperations operations1 = redisTemplate.opsForValue();
        Book book = new Book();
        book.setName("红楼梦");
        book.setAuthor("曹雪芹");
        operations1.set("b1", book);
        Object b1 = operations1.get("b1");
        System.out.println(b1);

    }

    @PostMapping("/{id}")
    public String echoId(@PathVariable Long id) {
        return String.valueOf(id);
    }
}
