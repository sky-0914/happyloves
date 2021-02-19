package cn.happyloves.netty.tcpPackage.customprotocol;

import lombok.Data;

/**
 * 自定义消息体
 *
 * @author zc
 * @date 2021/2/19 17:01
 */
@Data
public class CustomMessage {
    private int length;
    private byte[] content;
}
