package codec;

import data.MessageData;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author Tu Nguyen
 * @date 11:25 PM 2/20/2023
 */
public class Decoder implements ProtocolDecoder {
    @Override
    public void decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        byte[] received = new byte[ioBuffer.remaining()];
        ioBuffer.get(received);
        String data = new String(received);
        MessageData messageData = new MessageData(data);
        protocolDecoderOutput.write(messageData);
    }

    @Override
    public void finishDecode(IoSession ioSession, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
