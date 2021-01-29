package cn.happyloves.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattered 可以将数据写入到Buffer时，可以采用buffer数组，依次写入
 * Aggregating 从buffer读取数据时，可以采用buffer数组，依次读出
 *
 * @author zc
 * @date 2021/1/29 11:21
 */
public class NIOTest08ScatteredAndAggregating {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(6666));

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(3);
        byteBuffers[1] = ByteBuffer.allocate(5);

        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("有客户端连接");
        int messageLength = 8;
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                //将Channel内数据读取到Buffer数组
                long read = socketChannel.read(byteBuffers);
                byteRead += read;

                System.out.println("byteRead=" + byteRead);
                Arrays.stream(byteBuffers).map(buffer -> "position=" + buffer.position() + ",limit=" + buffer.limit()).forEach(System.out::println);
            }

            //翻转所有的Buffer
            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            int byteWrite = 0;
            while (byteWrite < messageLength) {
                //将Buffer数组内数据写入到Channel
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }

            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead=" + byteRead + ",byteWrite=" + byteWrite);
        }
    }
}
