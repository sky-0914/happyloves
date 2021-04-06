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

    private List<String> columnList;
    /**
     * 当前实体类里的子节点属性
     */
    private LambdaCriteria<?> sonLambdaCriteria;

    /**
     * 构造方法
     *
     * @param fn 函数式接口
     */
    public LambdaCriteria(SFunction<T, ?> fn) {
        this.init(fn, false);
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
        return Criteria.where(StringUtils.join(this.columnList, "."));
    }

    public Criteria lambdaWhere(SFunction<T, ?> fn) {
        return Criteria.where(ColumnUtil.getName(fn));
    }

    @SafeVarargs
    public final Criteria lambdaWhere(SFunction<T, ?>... fns) {
        List<String> columns = new ArrayList<>();
        for (SFunction<T, ?> fn : fns) {
            columns.add(ColumnUtil.getName(fn));
        }
        return Criteria.where(StringUtils.join(columns, "."));
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
        SerializedLambda serializedLambda = ColumnUtil.serializedLambda(fn);
        String methodName = serializedLambda.getImplMethodName();
        String prefix;
        if (methodName.startsWith(GET_PREFIX)) {
            prefix = GET_PREFIX;
        } else if (methodName.startsWith(IS_PREFIX)) {
            prefix = IS_PREFIX;
        } else {
            throw new RuntimeException("无效的getter方法: " + methodName);
        }
        // 从lambda信息取出method、field、class等
        String fName = serializedLambda.getImplMethodName().substring(prefix.length());
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
