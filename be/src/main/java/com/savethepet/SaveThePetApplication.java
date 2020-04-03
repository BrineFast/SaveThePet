package com.savethepet;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Main Application class
 *
 * @author Alexey Klimov
 */

@SpringBootApplication
public class SaveThePetApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaveThePetApplication.class, args);
    }

}


