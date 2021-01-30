package cn.happyloves.example.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 客户端
 * 1、连接服务器
 * 2、发送消息
 * 3、接收服务器消息
 *
 * @author ZC
 * @date 2021/1/30 18:45
 */
public class ClientTest1 {
    /**
     * 客户端SocketChannel
     */
    private final SocketChannel socketChannel;
    /**
     * 选择器Selector
     */
    private final Selector selector;
    private final static int PORT = 6666;
    private final static String ADDRESS = "127.0.0.1";
    private String username;

    public ClientTest1() throws IOException {
        //获取选择器selector
        selector = Selector.open();
        //创建客户端SocketChannel，并连接到服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(ADDRESS, PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将socketChannel注册到选择器selector，并注册事件SelectionKey.OP_READ
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");
    }

    /**
     * 发送数据
     *
     * @param data 数据
     * @throws IOException 异常信息
     */
    public void sendData(String data) throws IOException {
        data = username + " 说：" + data;
        socketChannel.write(ByteBuffer.wrap(data.getBytes()));
    }

    /**
     * 读取数据
     *
     * @throws IOException 异常信息
     */
    public void readDate() throws IOException {
        //获取是否有事件发生
        int select = selector.select();
        if (select > 0) {
            //获取所有有事件发生的SelectionKey
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //是否有读取事件
                if (selectionKey.isReadable()) {
                    //获取客户端的SocketChannel
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    //得到一个buffer
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    //读取
                    sc.read(buf);
                    //把缓冲区的数据转成字符串
                    String msg = new String(buf.array(), "UTF-8");
                    System.out.println(msg.trim());
                }
                //删除当前的SelectionKey，防止重复操作
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ClientTest1 clientTest = new ClientTest1();
        new Thread(() -> {
            while (true) {
                try {
                    clientTest.readDate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            clientTest.sendData(s);
        }
    }
}
