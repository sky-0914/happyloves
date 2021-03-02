package cn.happyloves.rpc.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zc
 * @date 2021/3/1 17:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcMessage implements Serializable {
    private static final long serialVersionUID = 430507739718447406L;
    private String name;
    private String methodName;
    private Class<?>[] parTypes;
    private Object[] pars;
    private Object result;
}
