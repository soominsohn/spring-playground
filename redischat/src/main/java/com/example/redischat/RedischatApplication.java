package com.example.redischat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedischatApplication implements CommandLineRunner {

    @Autowired
    private ChatService chatService;

    public static void main(String[] args) {
        SpringApplication.run(RedischatApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("application started!");

        chatService.enterChatRoom("chat1");
    }
}
