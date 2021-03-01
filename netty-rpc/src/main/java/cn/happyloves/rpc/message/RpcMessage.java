package cn.happyloves.rpc.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zc
 * @date 2021/3/1 17:43
 */
@Data
public class RpcMessage implements Serializable {
    private static final long serialVersionUID = 430507739718447406L;
    private String name;
    private String methodName;
    private Object[] pars;
    private Object result;
}
