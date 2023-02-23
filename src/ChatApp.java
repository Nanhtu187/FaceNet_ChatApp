import Client.ChatClient;
import Server.ChatServer;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Tu Nguyen
 * @date 9:45 AM 2/14/2023
 */



public class ChatApp {
    private static final Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
        ChatClient client1 = new ChatClient();
        client1.join(1);
        client1.message("Asdfasdf");
    }



}
