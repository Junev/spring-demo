package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;


@SpringBootApplication
@ServletComponentScan
@MapperScan("com.example.demo.core.infrastructure.mapper")
@MapperScan("com.example.repository.mapper")
@EnableCaching
public class DemoApplication {
    private static final Logger logger
            = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(DemoApplication.class);
        ConfigurableApplicationContext application = builder.bannerMode(Banner.Mode.OFF).run(args);

        Environment env = application.getEnvironment();
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String port = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        String path = contextPath != null ? contextPath : "";
//        System.out.println("\n----------------------------------------------------------\n\t" +
//                "Application demo is running! Access URLs:\n\t" +
//                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
//                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
//                "Swagger-ui: \thttp://" + ip + ":" + port + path + "/swagger-ui/index.html\n\t" +
////                "Doc文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
//                "----------------------------------------------------------");
        logger.info("\n----------------------------------------------------------\n\t" +
                "Application demo is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger-ui: \thttp://" + ip + ":" + port + path + "/swagger-ui/index.html\n" +
//                "Doc文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------\n");
    }


}
