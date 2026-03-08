package com.basic.myspringboot.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("myboot")
public class MyBootProperties {
}
