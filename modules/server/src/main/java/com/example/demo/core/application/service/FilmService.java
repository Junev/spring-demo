package com.example.demo.core.application.service;

import com.example.demo.core.domain.entity.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FilmService {
    @Autowired
    private RestTemplate restTemplate;

    public Film[] someRestCall(String name) {
        System.out.println(restTemplate);
        return this.restTemplate.getForObject("https://ghibliapi.herokuapp.com/films"
                , Film[].class);
    }
}
