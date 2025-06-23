package com.example.demo.core.interfaces.controller;

import com.example.demo.core.application.service.FilmService;
import com.example.demo.core.domain.entity.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/film")
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
