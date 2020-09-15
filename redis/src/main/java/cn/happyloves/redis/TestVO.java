package cn.happyloves.redis;

import lombok.Data;

import java.io.Serializable;

/**
 * Redis Session
 *
 * @author zc
 * @date 2020/9/15 19:12
 */
@Data
public class TestVO implements Serializable {
    private static final long serialVersionUID = -560035975886178417L;
    private String name;
}
