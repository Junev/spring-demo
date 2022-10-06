package com.example.demo.controller;

import com.example.demo.entity.Film;
import com.example.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilmController {
    @Autowired
    FilmService filmService;

    @GetMapping("/film")
    public String getFilm() {
        Film[] films = filmService.someRestCall("s");
        System.out.println(films.toString());
        System.out.println(films[0].getTitle());
        System.out.println(this);
        return "{" + films + "}";
    }
}
