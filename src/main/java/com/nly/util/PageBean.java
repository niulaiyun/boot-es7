package com.nly.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 参数
 */
@Data
public class PageBean {
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public Integer page = 1;

	public Integer pagerows = 10;
	/**
	 * 查询条件{"字段","值"}
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public Map<String, String> params;
	/**
	 * 范围查询最多3个最少2个 [["key","begin","endValue"]]
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public String[][] ranges;
	/**
	 * 排序多个用,分割 [["字段","desc"]]
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public String[][] sort;
	/**
	 * 关键字查询
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public String keyword;
	/**
	 * 关键字查询["字段"]
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public String[] keywords;

	/**
	 * 需要高亮的["字段"]
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public String[] highlightwords;

	public Integer total;

	public Long count;

	public List<?> rows;
	public Integer getTotal() {
		return (int) Math.ceil((double) count / pagerows);
	}
	
	public PageBean(List<?> rows,Long count) {
		this.rows=rows;
		this.count=count;
	}
	
	public PageBean() {
		
	}
}
