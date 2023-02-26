package Client;

import Data.MessageData;
import Server.ChatServer;
import codec.ServerCodecFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * @author Tu Nguyen
 * @date 12:09 AM 2/21/2023
 */
public class ChatClient {

    private int roomId;
    private IoSession session;

    public ChatClient() {
        IoConnector connector = new NioSocketConnector();
        //add filter, config
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter(new ServerCodecFactory()));
        connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        connector.setHandler(new ClientHandler());
        connector.setConnectTimeoutMillis(30);
        connector.getSessionConfig().setReadBufferSize(2048);
        join(0);
        //connect to server
        for(;;) {
            try {
                ConnectFuture future = connector.connect(new InetSocketAddress(9903));
                future.awaitUninterruptibly();
                session = future.getSession();
                break;
            }
            catch (Exception e) {
                System.out.println("fail to connect");
            }
        }
    }

    public String getAddress() {
        return session.getLocalAddress().toString();
    }

    public IoSession getSession() {
        return this.session;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void join(int roomId) {
        this.roomId = roomId;
        ChatServer.addToRoom(this);
    }

    public void left() {
        ChatServer.remove(this);
    }

    public void message(String s) {
        this.session.write(new MessageData(this.session, s, "message", this.roomId));
    }

    public static void main(String[] argv) {
       ChatClient client1 = new ChatClient();
       ChatClient client2 = new ChatClient();
       ChatClient client3 = new ChatClient();
       client1.message("1234543");
    }
}
