package cn.happyloves.example.nio;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读取文件内容并打印
 *
 * @author zc
 * @date 2021/1/27 09:58
 */
public class NIOTest02 {
    @SneakyThrows
    public static void main(String[] args) {
        final File file = new File("./example/NIOTest01/1.txt");
        //创建输入流
        final FileInputStream fileInputStream = new FileInputStream(file);
        //获取Channel
        final FileChannel channel = fileInputStream.getChannel();
        //定义ByteBuffer
        final ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        //将Channel内的数据，读取到缓冲区
        channel.read(buffer);
        //将ByteBuffer的字节数据转成String并打印
        System.out.println(new String(buffer.array()));
    }
}
