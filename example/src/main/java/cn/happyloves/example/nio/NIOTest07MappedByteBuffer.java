package cn.happyloves.example.nio;

import lombok.Cleanup;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可以让文件直接在内存中（堆外内存）修改，操作系统不需要拷贝一次
 *
 * @author zc
 * @date 2021/1/28 17:04
 */
public class NIOTest07MappedByteBuffer {
    public static void main(String[] args) throws IOException {
        @Cleanup final RandomAccessFile rw = new RandomAccessFile("./example/NIOTest03/1.txt", "rw");
        //获取对应通道
        final FileChannel channel = rw.getChannel();
        /**
         * 参数1：FileChannel.MapMode.READ_WRITE 读写模式
         * 参数2：可以修改的起始下标位置
         * 参数3：可以修改的长度
         */
        final MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, Byte.parseByte("A"));
        map.put(4, Byte.parseByte("0"));
    }
}
