package cn.happyloves.example.nio;

import java.nio.ByteBuffer;

/**
 * put,get
 * 按照类型去取，不然有可能乱码或者BufferUnderflowException异常
 *
 * @author zc
 * @date 2021/1/28 15:41
 */
public class NIOTest05BufferPutGet {
    public static void main(String[] args) {
        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.putInt(1);
        buffer.putLong(3);
        buffer.putDouble(5);

        buffer.flip();
        //BufferUnderflowException
//        System.out.println(buffer.getLong());
//        System.out.println(buffer.getLong());
//        System.out.println(buffer.getLong());

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
    }
}
