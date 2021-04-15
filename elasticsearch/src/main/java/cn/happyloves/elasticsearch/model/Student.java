package cn.happyloves.elasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zc
 * @date 2020/10/23 12:16
 */
@Data
@Document(indexName = "student")
public class Student implements Serializable {
    private static final long serialVersionUID = 4830083526353606264L;
    @Id
    private Long id;
    private String name;
    private int age;
    @Field
    private SexType sex;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 入学时间
     */
    private Date admissionTime;
}
