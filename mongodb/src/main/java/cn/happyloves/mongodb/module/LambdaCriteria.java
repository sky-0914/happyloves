package cn.happyloves.mongodb.module;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        assert StringUtils.isNotBlank(columnName) : "列名不能为空";
        return Criteria.where(this.getColumn());
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
    public LambdaCriteria<?> addSon(LambdaCriteria<?> son) {
        LambdaCriteria.setParentSon(this, son);
        return this;
    }

    /**
     * 获取列名
     *
     * @return 返回值
     */
    public String getColumn() {
        List<String> columnNames = new ArrayList<>();
        columnNames.add(this.getColumnName());
        LambdaCriteria.getColumnName(this, columnNames);
        return StringUtils.join(columnNames, ".");
    }

    /**
     * 静态方法-设置子列值
     *
     * @param parent 父
     * @param son    子
     */
    public static void setParentSon(LambdaCriteria<?> parent, LambdaCriteria<?> son) {
        if (parent.getSonLambdaCriteria() != null) {
            //递归
            setParentSon(parent.getSonLambdaCriteria(), son);
        } else {
            parent.setSonLambdaCriteria(son);
        }
    }

    /**
     * 静态方法-获取字段名
     *
     * @param parent      父
     * @param columnNames 字段名集合
     */
    public static void getColumnName(LambdaCriteria<?> parent, List<String> columnNames) {
        LambdaCriteria<?> son = parent.getSonLambdaCriteria();
        if (son != null) {
            final Class<?> fieldType = parent.field.getType();
            //当父字段类型与子实体类，类型不匹配
            assert fieldType == son.getEntityType() : fieldType + " & " + son.getEntityType() + ", 父字段类型与子实体类型不匹配";
            columnNames.add(son.getColumnName());
            //递归
            getColumnName(son, columnNames);
        }
    }

    /**
     * 初始化对象信息
     *
     * @param fn 函数式接口
     */
    private void init(SFunction<T, ?> fn, boolean isHump) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);

        // 从lambda信息取出method、field、class等
        String fieldName = serializedLambda.getImplMethodName().substring("get".length());
        fieldName = fieldName.replaceFirst(fieldName.charAt(0) + "", (fieldName.charAt(0) + "").toLowerCase());
        try {
            this.entityType = Class.forName(serializedLambda.getImplClass().replace("/", "."));
            this.field = this.entityType.getDeclaredField(fieldName);
            this.fieldName = fieldName;
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        // 从field取出字段名，可以根据实际情况调整
        ColumnName columnName = field.getAnnotation(ColumnName.class);
        if (columnName != null && StringUtils.isNotBlank(columnName.value())) {
            this.columnName = columnName.value();
        } else {
            //是否是驼峰，三元表达式
            this.columnName = isHump ? fieldName : fieldName.replaceAll("[A-Z]", "_$0").toLowerCase();
        }
    }
}
