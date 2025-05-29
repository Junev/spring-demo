package com.example.mytask;

import com.example.mytask.service.ScanOpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyTaskApplicationTests {

    @Autowired
    ScanOpcService service;

//    @Test
//    void contextLoads() {
//        assertThat(service).isNotNull();
//    }

}
