package Server;

import Client.ChatClient;
import Data.MessageData;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import codec.ServerCodecFactory;

import java.util.*;
import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * @author Tu Nguyen
 * @date 10:57 PM 2/20/2023
 */
public class ChatServer {

    public static Set<ChatClient> rooms = new HashSet<>();
    private static List<String> validIP = new ArrayList<>();

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

    public static void addValidIP(String s) {
        validIP.add(s);
    }

    public static boolean checkValidIP(String s) {
        return validIP.contains(s);
    }


    /**
     * sent message to all client.
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
            ChatServer.addValidIP(ip);
        }
        ChatClient client1 = new ChatClient();
        ChatClient client2 = new ChatClient();
        ChatClient client3 = new ChatClient();
        System.out.println(rooms.size());
        client1.message("1234543");
    }
}
