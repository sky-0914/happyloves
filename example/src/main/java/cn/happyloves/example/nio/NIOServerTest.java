package cn.happyloves.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 服务端
 *
 * @author zc
 * @date 2021/1/29 14:27
 */
public class NIOServerTest {
    public static void main(String[] args) throws IOException {
        //获取Selector对象
        final Selector selector = Selector.open();

        //创建服务端ServerSocketChannel =》 ServerSocket
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口，在服务端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel，注册到selector 监听事件 SelectionKey.OP_ACCEPT(连接)
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true) {
            //等待了1秒后，仍然没有获取到事件发生，返回
            if (selector.select(1000) == 0) {
                System.out.println("服务等待1秒，无连接");
                continue;
            }
            //如果返回>0，则代表已经发生了监听的事件
            //获取监听事件的集合 Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //通过SelectionKey反向获取Channel
            //使用迭代器循环处理
            final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                //获取到SelectionKey
                final SelectionKey selectionKey = iterator.next();
                //根据Key相对应Channel发生的事件做相应处理
                // 判断是否发生了连接事件，selectionKey.isAcceptable() == SelectionKey.OP_ACCEPT
                if (selectionKey.isAcceptable()) {
                    //通过serverSocketChannel 获取 socketChannel
                    final SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("连接到一个客户端");
                    //将socketChannel 注册到 selector 并绑定事件SelectionKey.OP_READ，同时关联一个缓冲区Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                // 判断是否发生的读取事件
                if (selectionKey.isReadable()) {
                    final SocketChannel channel = (SocketChannel) selectionKey.channel();
                    final ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    //将Channel数据读取到Buffer
                    channel.read(buffer);
                    //打印Buffer数据
                    System.out.println("客户端发送数据：" + new String(buffer.array(), "utf-8"));
                }

                iterator.remove();
            }

        }
    }
}
