package com.basic.myspringboot.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("myboot")
public class MyBootProperties {
    private String name;
    private int age;
    private String fullName;
}
