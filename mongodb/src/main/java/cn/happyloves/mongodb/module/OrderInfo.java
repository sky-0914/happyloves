package cn.happyloves.mongodb.module;

import lombok.Data;

import java.util.UUID;

/**
 * @author zc
 * @date 2021/4/7 22:28
 */
@Data
public class OrderInfo {
    private String id;

    private String no = UUID.randomUUID().toString();

    private double totalPrice;

}
