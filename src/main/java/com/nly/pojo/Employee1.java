package com.nly.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Document(indexName = "employee1")
public class Employee1 {
		@Id    
		public  String id;
		@Field
	    public  Long version;
		@Field
	    public  String firstName;
		@Field
	    public  String lastName;
		@Field
	    public  String age;
		@Field
	    public  String[] interests;
}
