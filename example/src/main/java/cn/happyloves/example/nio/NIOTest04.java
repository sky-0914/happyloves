package cn.happyloves.example.nio;

import lombok.Cleanup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 拷贝图片
 * transferFrom直接拷贝
 *
 * @author zc
 * @date 2021/1/28 14:05
 */
public class NIOTest04 {
    public static void main(String[] args) throws IOException {
        @Cleanup final FileInputStream fileInputStream = new FileInputStream("./example/NIOTest04/1.png");
        @Cleanup final FileChannel source = fileInputStream.getChannel();

        @Cleanup final FileOutputStream fileOutputStream = new FileOutputStream("./example/NIOTest04/2.png");
        @Cleanup final FileChannel target = fileOutputStream.getChannel();

        //使用transferFrom完成拷贝。
        target.transferFrom(source, 0, source.size());
    }
}
