package cn.happyloves.mongodb.module;


import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author ZC
 * @date 2021/4/2 14:16
 */
public class LambdaCriteria<T> extends Criteria {

    public Criteria lambdaWhere(SFunction<T, ?> fn) {
        return Criteria.where(ColumnUtil.getName(fn));
    }
}
