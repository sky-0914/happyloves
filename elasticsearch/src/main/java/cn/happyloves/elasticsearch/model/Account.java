package cn.happyloves.elasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author ZC
 * @date 2021/4/14 11:08
 */
@Data
@Document(indexName = "account")
public class Account implements Serializable {

    private static final long serialVersionUID = -1281501190959977855L;

    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String username;
    private String password;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Integer)
    private int age;
    @Field(type = FieldType.Integer)
    private SexType sex;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createTime;
    /**
     * 入学时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
