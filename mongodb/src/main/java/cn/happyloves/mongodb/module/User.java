package cn.happyloves.mongodb.module;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author zc
 * @date 2021/4/5 21:56
 */
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private Account account;
}
