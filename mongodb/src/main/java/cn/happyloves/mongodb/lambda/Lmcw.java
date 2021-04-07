package cn.happyloves.mongodb.lambda;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * LambdaMongoColumnWrapper
 * 莱姆达Mongo字段包装器
 *
 * @author ZC
 * @date 2021/4/2 14:16
 */
@Slf4j
public class Lmcw<T> {

    /**
     * 当前实体类型
     */
    private Class<?> entityType;
    /**
     * 当前实体类里的字段
     */
    private Field field;
    /**
     * 当前实体类里的对应数据库列名
     */
    private String columnName;
    /**
     * 所有字段集合
     */
    private List<String> columnList;
    /**
     * 当前实体类里的子节点属性
     */
    private Lmcw<?> sonLambdaCriteria;

    public static <T> Lmcw<T> create(MongoFunction<T, ?> fn) {
        return new Lmcw<>(fn);
    }

    /**
     * 构造方法
     *
     * @param fn 函数式接口
     */
    private Lmcw(MongoFunction<T, ?> fn) {
        this(fn, false);
    }

    /**
     * 构造方法
     *
     * @param fn     函数式结构
     * @param isHump 是否驼峰
     */
    private Lmcw(MongoFunction<T, ?> fn, boolean isHump) {
        this.init(fn, isHump);
    }

    /**
     * 添加子列
     *
     * @param son 子列值
     * @return 返回值
     */
    public Lmcw<T> son(Lmcw<?> son) {
        Lmcw.setParentSon(this, son);
        return this;
    }

    /**
     * 获取列名
     *
     * @return 返回值
     */
    @Override
    public String toString() {
        return StringUtils.join(this.columnList, ".");
    }

    public static final String GET_PREFIX = "get";
    public static final String IS_PREFIX = "is";

    /**
     * 初始化对象信息
     *
     * @param fn 函数式接口
     */
    private void init(MongoFunction<T, ?> fn, boolean isHump) {
        //获取序列化Lambda对象
        SerializedLambda serializedLambda = Lmcw.serializedLambda(fn);
        //获取方法名
        String methodName = serializedLambda.getImplMethodName();
        //判断方法名前缀
        String prefix;
        if (methodName.startsWith(GET_PREFIX)) {
            prefix = GET_PREFIX;
        } else if (methodName.startsWith(IS_PREFIX)) {
            prefix = IS_PREFIX;
        } else {
            throw new RuntimeException("无效的getter方法: " + methodName);
        }
        // 从lambda信息取出method、field、class等
        String fName = methodName.substring(prefix.length());
        fName = fName.replaceFirst(fName.charAt(0) + "", (fName.charAt(0) + "").toLowerCase());
        try {
            this.entityType = Class.forName(serializedLambda.getImplClass().replace("/", "."));
            this.field = this.entityType.getDeclaredField(fName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        // 从field取出字段名，可以根据实际情况调整,如果被MongoDB，@Field注解标识的就取注解值
        org.springframework.data.mongodb.core.mapping.Field cName = field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);
        if (cName != null && StringUtils.isNotBlank(cName.value())) {
            this.columnName = cName.value();
        } else {
            //是否是驼峰，三元表达式
            this.columnName = isHump ? fName : fName.replaceAll("[A-Z]", "_$0").toLowerCase();
        }
        this.columnList = new ArrayList<>();
        this.columnList.add(this.columnName);
    }


    public static <T> SerializedLambda serializedLambda(MongoFunction<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        //isAccessible()值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
        //实际上setAccessible是启用和禁用访问安全检查的开关,并不是为true就能访问为false就不能访问
        //由于JDK的安全检查耗时较多.所以通过setAccessible(true)的方式关闭安全检查就可以达到提升反射速度的目的
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);
        return serializedLambda;
    }

    public static <T> String getName(MongoFunction<T, ?> fn) {
        SerializedLambda serializedLambda = serializedLambda(fn);
        // 从lambda信息取出method、field、class等
        String fName = serializedLambda.getImplMethodName().substring("get".length());
        return fName.replaceFirst(fName.charAt(0) + "", (fName.charAt(0) + "").toLowerCase());
    }

    /**
     * 静态方法-设置子列值
     *
     * @param parent 父
     * @param son    子
     */
    public static void setParentSon(@NonNull Lmcw<?> parent, @NonNull Lmcw<?> son) {
        parent.columnList.add(son.columnName);
        if (parent.sonLambdaCriteria != null) {
            //递归
            setParentSon(parent.sonLambdaCriteria, son);
        } else {
            //获取父字段类型
            final Class<?> fieldType = parent.field.getType();
            //当父字段类型与子实体类，类型不匹配
            if (fieldType != son.entityType) {
                throw new RuntimeException(fieldType + " & " + son.entityType + ", 父字段类型与子实体类型不匹配");
            }
            //设置子类
            parent.sonLambdaCriteria = son;
        }
    }

    /**
     * 使Function获取序列化能力
     *
     * @author ZC
     * @date 2021/4/2 14:32
     */
    @FunctionalInterface
    public interface MongoFunction<T, R> extends Function<T, R>, Serializable {

    }
}
