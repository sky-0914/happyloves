package cn.happyloves.netty.tcpPackage.customprotocol.coder;

import cn.happyloves.netty.tcpPackage.customprotocol.CustomMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 *
 * @author zc
 * @date 2021/2/19 18:31
 */
public class MessageEncoder extends MessageToByteEncoder<CustomMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CustomMessage customMessage, ByteBuf byteBuf) throws Exception {
        System.out.println("====== 编码 ======");
        byteBuf.writeInt(customMessage.getLength());
        byteBuf.writeBytes(customMessage.getContent());
    }
}
