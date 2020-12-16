package com.glen.assignment;

import com.glen.assignment.common.utils.properties.EventProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(EventProperties.class)
public class WebScrapperApplication {


    public static void main(String[] args) {
        SpringApplication.run(WebScrapperApplication.class, args);

    }

}
