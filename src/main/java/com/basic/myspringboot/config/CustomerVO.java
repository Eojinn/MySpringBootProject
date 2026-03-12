package com.basic.myspringboot.config;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Builder
@ToString
@Component
@Data
@NoArgsConstructor  // 👈 1. 기본 생성자를 만들어줍니다 (스프링 필수)
@AllArgsConstructor // 👈 2. 모든 필드를 가진 생성자 (필요시 사용)
public class CustomerVO {
    private String mode;
    private double rate;
}

