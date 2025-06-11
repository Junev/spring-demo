package com.example.demo.core.interfaces.controller;

import com.example.demo.core.domain.entity.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    Book book;

    @Autowired
    RedisClient redisClient;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping("")
    public Book book() {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisAsyncCommands<String, String> asyncCommands = connect.async();
        RedisFuture<String> get1 = asyncCommands.get("name");
        RedisFuture<String> set1 = asyncCommands.set("name", "foo bar");

        ArrayList<RedisFuture<String>> redisFutures = new ArrayList<>();
        redisFutures.add(get1);
        redisFutures.add(set1);
        LettuceFutures.awaitAll(1, TimeUnit.MINUTES,
                redisFutures.toArray(new RedisFuture[0]));
        connect.closeAsync();


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

        ValueOperations<String, String> operations1 = redisTemplate.opsForValue();
        Book book = new Book();
        book.setName("红楼梦");
        book.setAuthor("曹雪芹");
        String bookStr = null;
        try {
            bookStr = objectMapper.writeValueAsString(book);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        operations1.set("b1", bookStr);

        Object b1 = operations1.get("b1");
        System.out.println(b1);

    }

    @PostMapping("/{id}")
    public String echoId(@PathVariable Long id) {
        return String.valueOf(id);
    }
}
