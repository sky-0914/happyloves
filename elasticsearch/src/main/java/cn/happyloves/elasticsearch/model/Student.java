package cn.happyloves.elasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

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
    private String sex;
}
