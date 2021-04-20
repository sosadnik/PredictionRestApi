package org.PredictionRestApi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Controller
@SpringBootApplication
@RequiredArgsConstructor
@EnableSwagger2
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
