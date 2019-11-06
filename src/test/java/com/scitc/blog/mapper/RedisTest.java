package com.scitc.blog.mapper;

import com.scitc.blog.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {


    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void testSet() {
        redisUtils.set("username", "xiaoxianyu");


    }
    @Test
    public void testGet(){
        String username = redisUtils.get("username");
        System.out.println("username:" + username);

    }

}
