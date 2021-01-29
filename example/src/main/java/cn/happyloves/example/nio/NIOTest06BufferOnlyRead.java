package cn.happyloves.example.nio;

import java.nio.ByteBuffer;

/**
 * 只读Buffer
 * java.nio.HeapByteBufferR
 *
 * @author zc
 * @date 2021/1/28 16:57
 */
public class NIOTest06BufferOnlyRead {
    public static void main(String[] args) {
        final ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.put("张三".getBytes());
        buffer.put("李四".getBytes());

        for (int i = 0; i < 10; i++) {
            buffer.put((byte) i);
        }

        buffer.flip();

        final ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

        //只读Buffer，再次写入抛出异常：java.nio.ReadOnlyBufferException
//        readOnlyBuffer.put((byte) 10);

    }

}
