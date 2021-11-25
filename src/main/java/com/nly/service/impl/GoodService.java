package com.nly.service.impl;

import com.nly.base.service.impl.BaseESService;
import org.springframework.stereotype.Service;

import com.nly.pojo.Good;
import com.nly.service.IGoodService;
@Service
public class GoodService  extends BaseESService<Good, String> implements IGoodService{

	@Override
	public Good createEntity() {
		
		return new Good();
	}

}
