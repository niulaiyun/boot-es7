package com.nly.controller;

import com.nly.base.controller.BaseESController;
import com.nly.base.service.IBaseESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nly.pojo.Article;
import com.nly.service.IArticleService;


@RestController
@RequestMapping("/article")
public  class ArticleController extends BaseESController<Article, String> {

	@Autowired
	IArticleService articleService;

	@Override
	public IBaseESService<Article, String> getBaseESService() {
		return articleService;
	}


}
