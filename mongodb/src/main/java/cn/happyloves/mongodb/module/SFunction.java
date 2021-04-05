package cn.happyloves.mongodb.module;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 使Function获取序列化能力
 * @author ZC
 * @date 2021/4/2 14:32
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
