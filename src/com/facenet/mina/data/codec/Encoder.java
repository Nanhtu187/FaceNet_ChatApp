package com.facenet.mina.data.codec;

import com.facenet.mina.data.MessageData;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;



/**
 * @author Tu Nguyen
 * @date 11:22 PM 2/20/2023
 */
public class Encoder implements ProtocolEncoder {

    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        if (o instanceof MessageData) {
            IoBuffer buffer = IoBuffer.allocate(12).setAutoExpand(true);
            buffer.put(((MessageData) o).getMessage().getBytes());
            buffer.flip();
            protocolEncoderOutput.write(buffer);
        }
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
