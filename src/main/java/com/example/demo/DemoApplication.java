package com.example.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class DemoApplication {

    public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(DemoApplication.class);
        builder.bannerMode(Banner.Mode.OFF).run(args);
    }

}
