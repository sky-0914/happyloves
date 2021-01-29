package cn.happyloves.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 *
 * @author zc
 * @date 2021/1/29 15:52
 */
public class NIOClientTest {
    public static void main(String[] args) throws IOException {
        //创建客户端socketChannel
        final SocketChannel socketChannel = SocketChannel.open();
        //设置成非阻塞
        socketChannel.configureBlocking(false);
        //连接服务端，不成功的话继续连接
        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1", 6666))) {
            //当完成连接的时候
            while (!socketChannel.finishConnect()) {
                System.out.println("连接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }

        String str = "ABCDEFG,12345,上山打老虎";
        //获取ByteBuffer，wrap a bytes 包装字节数组
        final ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //将缓冲区Buffer数据写入到Channel
        socketChannel.write(buffer);
        System.in.read();
    }
}
