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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "article")//索引名称 建议与实体类一致
public class   Article{
    @Id
    public  String id;
    
    @Field(type = FieldType.Text,index=true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    public String title;
    
    @Field(type = FieldType.Text,index=true, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    public String content;
    
   
    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
	private Date createTime;
    
    
/*	@Field(type = FieldType.Date,format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;*/
}


