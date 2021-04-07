package cn.happyloves.mongodb.module;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 条件构造器
 * @author ZC
 * @date 2021/4/2 14:16
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class LambdaCriteria<T> extends Criteria {

    /**
     * 当前实体类型
     */
    private Class<?> entityType;
    /**
     * 当前实体类里的字段
     */
    private Field field;
    /**
     * 当前实体类里的字段名
     */
    private String fieldName;
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
    private LambdaCriteria<?> sonLambdaCriteria;

    private Criteria criteria;

    public static <T> LambdaCriteria<T> create(SFunction<T, ?> fn) {
        return new LambdaCriteria<>(fn);
    }

    /**
     * 构造方法
     *
     * @param fn 函数式接口
     */
    public LambdaCriteria(SFunction<T, ?> fn) {
        this.init(fn, false);
    }

    public Criteria getCriteria() {
        this.criteria = Criteria.where(StringUtils.join(this.columnList, "."));
        return this.criteria;
    }

    /**
     * 构造方法
     *
     * @param fn     函数式结构
     * @param isHump 是否驼峰
     */
    public LambdaCriteria(SFunction<T, ?> fn, boolean isHump) {
        this.init(fn, isHump);
    }

    public Criteria lambdaWhere() {
        if (this.columnList.isEmpty()) {
            throw new RuntimeException("列名不能为空");
        }
        return this.getCriteria();
    }

//    public Criteria lambdaWhere(SFunction<T, ?> fn) {
//        return Criteria.where(ColumnUtil.getName(fn));
//    }

    public Criteria lambdaWhere(SFunction<T, ?> fn) {
        LambdaCriteria<T> lambdaCriteria = new LambdaCriteria<>(fn);
        return this.getCriteria();
    }

//    @SafeVarargs
//    public final Criteria lambdaWhere(SFunction<T, ?>... fns) {
//        List<String> columns = new ArrayList<>();
//        for (SFunction<T, ?> fn : fns) {
//            columns.add(ColumnUtil.getName(fn));
//        }
//        return Criteria.where(StringUtils.join(columns, "."));
//    }

    @SafeVarargs
    public final Criteria lambdaWhere(SFunction<T, ?>... fns) {
        LambdaCriteria<T> lambdaCriteria = null;
        for (SFunction<T, ?> fn : fns) {
            if (lambdaCriteria == null) {
                lambdaCriteria = new LambdaCriteria<>(fn);
            } else {
                lambdaCriteria.addSon(new LambdaCriteria<>(fn));
            }
        }
        return this.getCriteria();
    }

    public Criteria and(SFunction<T, ?> fn) {
        LambdaCriteria<T> lambdaCriteria = new LambdaCriteria<>(fn);
        return this.criteria.and(StringUtils.join(lambdaCriteria.columnList, "."));
    }

    /**
     * 添加子列
     *
     * @param son 子列值
     * @return 返回值
     */
    public LambdaCriteria<T> addSon(LambdaCriteria<?> son) {
        ColumnUtil.setParentSon(this, son);
        return this;
    }

    /**
     * 获取列名
     *
     * @return 返回值
     */
    public String getColumn() {
        return StringUtils.join(this.columnList, ".");
    }

    public static final String GET_PREFIX = "get";
    public static final String IS_PREFIX = "is";

    /**
     * 初始化对象信息
     *
     * @param fn 函数式接口
     */
    private void init(SFunction<T, ?> fn, boolean isHump) {
        //获取序列化Lambda对象
        SerializedLambda serializedLambda = ColumnUtil.serializedLambda(fn);
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
            this.fieldName = fName;
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        // 从field取出字段名，可以根据实际情况调整
        ColumnName cName = field.getAnnotation(ColumnName.class);
        if (cName != null && StringUtils.isNotBlank(cName.value())) {
            this.columnName = cName.value();
        } else {
            //是否是驼峰，三元表达式
            this.columnName = isHump ? this.fieldName : this.fieldName.replaceAll("[A-Z]", "_$0").toLowerCase();
        }
        this.columnList = new ArrayList<>();
        this.columnList.add(this.columnName);
    }
}
