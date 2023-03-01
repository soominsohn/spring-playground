package com.example.redischat;

import java.util.Scanner;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements MessageListener {

    public void enterChatRoom(String chatRoomName) {
        final Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.equals("quit")) {
                System.out.println("Quit...");
                break;
            }
        }
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("Message: " + message.toString());

    }
}
