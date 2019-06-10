package com.springboot.practice.readinglist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadinglistApplicationTests {

    @Test
    public void contextLoads() {
        Map<String, String> content = new HashMap<>();
        content.put("11", "22");
        Map<String, String> test = Collections.unmodifiableMap(content);
        System.out.println(content.get("11"));

        content.put("11", "55");
        System.out.println(content.get("11"));
    }

}
