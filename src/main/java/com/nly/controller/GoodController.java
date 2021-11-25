package com.nly.controller;

import com.nly.base.controller.BaseESController;
import com.nly.base.service.IBaseESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nly.pojo.Good;
import com.nly.service.IGoodService;

@RestController
@RequestMapping("/good")
public class GoodController extends BaseESController<Good, String> {

	@Autowired
	IGoodService goodService;


	@Override
	public IBaseESService<Good, String> getBaseESService() {
		return goodService;
	}
}
