package com.savethepet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main Application class
 *
 * @author Alexey Klimov
 */
@SpringBootApplication
@EnableTransactionManagement
public class SaveThePetApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaveThePetApplication.class, args);
    }

}


