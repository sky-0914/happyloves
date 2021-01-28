package cn.happyloves.example.nio;

import lombok.SneakyThrows;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 将字符串写到文件
 *
 * @author zc
 * @date 2021/1/27 09:58
 */
public class NIOTest01 {
    @SneakyThrows
    public static void main(String[] args) {
        String str = "石头人";
        //创建输出流（写入）
        FileOutputStream fileOutputStream = new FileOutputStream("./example/NIOTest01/1.txt");
        //获取channel，对应的真实类型是FileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();
        //定义缓冲区ByteBuffer，设置长度1024
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将字符串写入到缓冲区
        byteBuffer.put(str.getBytes());
        //翻转->将写入反转成读取
        byteBuffer.flip();
        //将缓冲区数据写入到Channel
        channel.write(byteBuffer);
        fileOutputStream.close();
    }
}
