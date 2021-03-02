package cn.happyloves.netty.rpc.examplex.server.service;

import lombok.Data;

@Data
public class Account {
    private Integer id;

    private String name;
    private String username;
    private String password;
}
