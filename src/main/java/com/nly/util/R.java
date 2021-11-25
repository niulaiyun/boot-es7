package com.nly.util;

import java.util.List;

public class R {

	public Integer code=200;
	public String msg;
	
	public Object data;
    public Integer result=1;
    
	public static R result(List<?> rows,String msg) {
		R r = new R();
		r.data = rows;
		r.msg=msg;
		return r;
	}
	public static R result(List<?> rows) {
		R r = new R();
		r.data = rows;
		return r;
	}
	
	public static R result(Object  data) {
		R r = new R();
		r.data = data;
		return r;
	}
	public static R result(Object  data,String msg) {
		R r = new R();
		r.data = data;
		r.msg=msg;
		return r;
	}
	public static R result(PageBean pageBean) {
		R r = new R();
		r.data = pageBean;
		return r;
	}
	
	public static R result(PageBean pageBean,String msg) {
		R r = new R();
		r.data = pageBean;
		r.msg=msg;
		return r;
	}
}
