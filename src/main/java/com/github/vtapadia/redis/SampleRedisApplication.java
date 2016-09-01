package com.github.vtapadia.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class SampleRedisApplication implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate template;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleRedisApplication.class, args).close();
    }

    private String SAMPLE_KEY = "sample.key";

    public void run(String... strings) throws Exception {
        template.opsForValue().set(SAMPLE_KEY, String.valueOf(Long.MAX_VALUE-3L));

        for (int i=0; i<5; i++) {
            System.out.println(getNextValue());
        }
    }

    public Long getNextValue() {
        try {
            return template.opsForValue().increment(SAMPLE_KEY, 1);
        } catch (Exception e) {
            template.delete(SAMPLE_KEY);
            return template.opsForValue().increment(SAMPLE_KEY, 1);
        }
    }
}
