package cn.happyloves.example.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zc
 * @date 2021/1/28 10:17
 */
public class BIOTest {
    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(9999);

        // server将一直等待连接的到来
        System.out.println("server将一直等待连接的到来");
        Socket socket = serverSocket.accept();
        // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, len, "UTF-8"));
            //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            sb.append(new String(bytes, 0, len, "UTF-8"));
        }
        System.out.println("get message from client: " + sb);
        inputStream.close();
        socket.close();
        serverSocket.close();

    }
}
