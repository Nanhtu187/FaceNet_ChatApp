package com.facenet.mina.data.server;

import com.facenet.mina.data.client.ChatClient;
import com.facenet.mina.data.MessageData;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.facenet.mina.data.codec.ServerCodecFactory;

import java.util.*;
import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * @author Tu Nguyen
 * @date 10:57 PM 2/20/2023
 */
public class ChatServer {

    public static Set<ChatClient> rooms = new HashSet<>();
    private static Map<String, Integer> validIP = new HashMap<>();

    public ChatServer() throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        // add filter and config
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(new ServerCodecFactory()));

        acceptor.setHandler(new ServerHandler());
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.bind(new InetSocketAddress(9903));
    }

    public static void addToRoom(ChatClient client) {
        rooms.add(client);
    }


    public static void remove(ChatClient client) {
        rooms.remove(client);
    }

    public static void addValidIP(String s, int count) {
        validIP.put(s, count);
    }

    public static boolean checkValidIP(String s) {
        if(validIP.keySet().contains(s)){
            int limit = validIP.get(s);
            int count = 0;
            for (ChatClient client: rooms) {
                if(client.getAddress().equals(s)) ++ count;
            }
            return count < limit;
        } else return false;
    }


    /**
     * sent message to all com.facenet.mina.data.client.
     * @param s message.
     */

    public static void broadcast(Object s) {
        long roomId = ((MessageData)s).getRoom();
        long id = ((MessageData)s).getFrom();
        for(ChatClient client: rooms) {
            if(client.getRoomId() == roomId && client.getSession().getId() != id) {
                System.out.println(client.getSession().getId() + " received: \n" + s);
            }
        }
    }

    public static void main(String[] argv) throws IOException {
        Scanner in = new Scanner(System.in);
        ChatServer server = new ChatServer();
        System.out.println("Number of valid ip:");
        int count = in.nextInt();
        for(int i = 1; i <= count; ++ i) {
            System.out.println("Enter valid ip: ");
            String ip = in.next();
            System.out.println("Enter number of connection: ");
            int number = in.nextInt();
            ChatServer.addValidIP(ip, number);
        }
        ChatClient client1 = new ChatClient();
        ChatClient client2 = new ChatClient();
        ChatClient client3 = new ChatClient();
        System.out.println(rooms.size());
        client1.message("1234543");
    }
}
