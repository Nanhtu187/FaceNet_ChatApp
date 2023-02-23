package Server;

import Data.MessageData;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.FilterEvent;


/**
 * @author Tu Nguyen
 * @date 12:04 AM 2/21/2023
 */
public class ServerHandler implements IoHandler {

    private static final long DEFAULT_ROOM = 0;

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {
    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {
    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
        ioSession.write(new MessageData(ioSession, "ping pong", "keepalive", DEFAULT_ROOM));
    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {

    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        if(o instanceof MessageData) {
            ChatServer.broadcast(o);
        }
    }
    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {

    }

    @Override
    public void event(IoSession ioSession, FilterEvent filterEvent) throws Exception {

    }
}
