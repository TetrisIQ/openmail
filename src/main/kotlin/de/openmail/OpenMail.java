package de.openmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.swing.*;

@ComponentScan({"de.openmail.repository", "de.openmail.model", "de.openmail.config", "de.openmail.controller"})
@SpringBootApplication
public class OpenMail {

    public static void main(String[] args) {
        SpringApplication.run(OpenMail.class, args);
    }
}
