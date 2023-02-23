package Server;

import Client.ChatClient;
import Data.MessageData;
import org.apache.mina.core.IoUtil;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
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

    private static final Set<ChatClient> rooms = new HashSet<>();

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


    /**
     * sent message to all client.
     * @param s message.
     */

    public static void broadcast(Object s) {
        List<IoSession> sessions = new ArrayList<>();
        long roomId = ((MessageData)s).getRoom();
        long id = ((MessageData)s).getFrom();
        for(ChatClient client: rooms) {
            if(client.getRoomId() == roomId && client.getSession().getId() != id) {
                sessions.add(client.getSession());
            }
        }
        IoUtil.broadcast(s, sessions);
    }

    public static void main(String[] argv) throws IOException {
        ChatServer server = new ChatServer();
    }
}
