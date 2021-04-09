package cn.happyloves.netty.rpc.examplex.server.service;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZC
 * @date 2021/3/2 23:59
 */
@Data
public class Account implements Serializable {
    private static final long serialVersionUID = 667178018106218163L;
    private Integer id;

    private String name;
    private String username;
    private String password;
}
