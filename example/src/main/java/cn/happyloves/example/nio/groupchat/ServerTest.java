package cn.happyloves.example.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 服务器端
 * 1、服务器启动并监听6666
 * 2、服务器接收客户端消息，并实现转发，（处理上线和离线）
 *
 * @author ZC
 * @date 2021/1/30 17:02
 */
public class ServerTest {

    /**
     * 选择器
     */
    private final Selector selector;
    /**
     * 服务端的serverSocketChannel
     */
    private final ServerSocketChannel serverSocketChannel;
    /**
     * 监听端口
     */
    private static final int PORT = 6666;

    public ServerTest() throws IOException {
        //获取选择器
        selector = Selector.open();
        //获取serverSocketChannel
        serverSocketChannel = ServerSocketChannel.open();
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //将serverSocketChannel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {
        while (true) {
            //获取是否有事件发生
            int count = selector.select();
            if (count > 0) {
                //有事件发生，需要处理
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    //获取SelectionKey
                    SelectionKey selectionKey = iterator.next();
                    //判断是否是Accept事件
                    if (selectionKey.isAcceptable()) {
                        //获取的客户端SocketChannel
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //设置非zus
                        socketChannel.configureBlocking(false);
                        //将客户端的Channel注册到Selector选择器
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println(socketChannel.getRemoteAddress() + "上线了。。。");
                    }
                    //判断是否有读取事件
                    if (selectionKey.isReadable()) {
                        //读取数据
                        this.readData(selectionKey);
                    }
                    iterator.remove();
                }
            }

        }
    }

    /**
     * 读取数据
     *
     * @param selectionKey 事件Key
     */
    public void readData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            //通过事件Key 获取客户端Channel
            socketChannel = (SocketChannel) selectionKey.channel();
            //获取Buffer缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //将Channel数据读取到缓冲区Buffer
            int read = socketChannel.read(buffer);
            //判断是否读取到数据
            if (read > 0) {
                //将数据转成字符串
                String msg = new String(buffer.array(), "UTF-8");
                System.out.println("客户端发消息：" + msg);
                //发送消息到客户端
                this.sendAllDate(socketChannel, msg);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线了...");
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    /**
     * 发送数据到客户端，并且排除自己
     *
     * @param self 自己的客户端
     * @param msg  消息内容
     * @throws IOException 异常信息
     */
    public void sendAllDate(SocketChannel self, String msg) throws IOException {
        System.out.println("服务器转发消息中。。。");
        for (SelectionKey selectionKey : selector.keys()) {
            Channel channel = selectionKey.channel();
            if (channel instanceof SocketChannel && channel != self) {
                SocketChannel socketChannel = (SocketChannel) channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                socketChannel.write(buffer);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerTest serverTest = new ServerTest();
        serverTest.listen();
    }
}
