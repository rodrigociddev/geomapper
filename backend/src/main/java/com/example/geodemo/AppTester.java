package com.example.geodemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Runs code after application start */
@Component
public class AppTester implements CommandLineRunner {

    /**
     * Put test code in this method
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner here!");
    }
}
