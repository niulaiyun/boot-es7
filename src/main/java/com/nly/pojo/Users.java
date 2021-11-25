package com.nly.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "users")//索引名称 建议与实体类一致
public class Users {
    @Id
    public Integer id;
    @Field(type = FieldType.Auto)//自动检测类型
    public Integer age;
    @Field(type = FieldType.Keyword)//手动设置为keyword  但同时也就不能分词
    public String name;
    
    @Field(type = FieldType.Text,index=true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    public String info;
}


