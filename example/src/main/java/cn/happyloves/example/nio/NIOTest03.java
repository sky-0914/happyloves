package cn.happyloves.example.nio;

import lombok.Cleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 复制文件
 *
 * @author zc
 * @date 2021/1/28 14:05
 */
public class NIOTest03 {
    public static void main(String[] args) throws IOException {
        File file = new File("./example/NIOTest03/1.txt");
        //创建输入流，读取文件
        @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
        //获取Channel
        @Cleanup FileChannel channel1 = fileInputStream.getChannel();
        //创建输出流，写入新文件
        @Cleanup FileOutputStream fileOutputStream = new FileOutputStream("./example/NIOTest03/2.txt");
        //获取Channel
        @Cleanup FileChannel channel2 = fileOutputStream.getChannel();
//        //创建缓冲区ByteBuffer
//        final ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
//        //将Channel内的数据，读取到缓冲区
//        channel1.read(buffer);
//        //翻转
//        buffer.flip();
//        //将缓冲区内的数据，写入到Channel
//        channel2.write(buffer);

        //循环读取
        final ByteBuffer buffer = ByteBuffer.allocate(10);
        while (true) {
            /**
             *     //将Buffer复位
             *     public final Buffer clear() {
             *         position = 0;
             *         limit = capacity;
             *         mark = -1;
             *         return this;
             *     }
             */
            buffer.clear();
            final int read = channel1.read(buffer);
            System.out.println(read);
            if (read == -1) {
                //读取完了
                break;
            }
            buffer.flip();
            channel2.write(buffer);
        }
    }
}