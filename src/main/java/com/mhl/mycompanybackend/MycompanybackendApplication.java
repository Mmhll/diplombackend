package com.mhl.mycompanybackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@OpenAPIDefinition
public class MycompanybackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycompanybackendApplication.class, args);
    }

}
