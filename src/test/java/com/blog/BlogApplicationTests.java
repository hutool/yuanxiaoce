package com.blog;

import com.blog.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
        String s = MD5Util.MD5Encode("123456", "UTF-8");
        System.out.println(s);
    }

}
