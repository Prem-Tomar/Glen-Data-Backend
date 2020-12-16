package com.glen.assignment.common.utils.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:message.properties")
@ConfigurationProperties(prefix = "event")
@Getter
@Setter
public class EventProperties {
    private String message;
    private String description;
}
