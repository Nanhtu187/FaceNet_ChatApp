import Client.ChatClient;
import Server.ChatServer;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Tu Nguyen
 * @date 9:45 AM 2/14/2023
 */



public class ChatApp {
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
        System.out.println("Number of valid ip:");
        int count = in.nextInt();
        for(int i = 1; i <= count; ++ i) {
            System.out.println("Enter valid ip: ");
            String ip = in.next();
            ChatServer.addValidIP(ip);
        }
        ChatClient client1 = new ChatClient();
        ChatClient client2 = new ChatClient();
        client1.message("1234543");
    }



}
