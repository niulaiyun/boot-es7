package com.nly.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 索引名称 建议与实体类一致
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "good")
public class   Good{
    @Id
    public  String id;
    
    
    @Field(type = FieldType.Keyword)
    public String name;
    
    
    @Field(type = FieldType.Text,index=true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    public String title;
    /**
     *  分类
     */
    @Field(type = FieldType.Keyword)
    public String category;
    /**
     * 品牌
     */
    @Field(type = FieldType.Keyword)
    public String brand;
    /**
     * 价格
     */
    @Field(type = FieldType.Double)
    public Double price;
    
    
	@Field(type = FieldType.Date, format = DateFormat.custom,pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd",timezone="GMT+8")
	public Date createTime;
    
    
	@Field(type = FieldType.Date,format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date updateTime;

}


