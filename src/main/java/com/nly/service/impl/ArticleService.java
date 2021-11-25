package com.nly.service.impl;

import com.nly.base.service.impl.BaseESService;
import org.springframework.stereotype.Service;

import com.nly.pojo.Article;
import com.nly.service.IArticleService;

/**
 *
 */
@Service
public class ArticleService  extends BaseESService<Article, String> implements IArticleService{

	@Override
	public Article createEntity() {
		
		return new Article();
	}

}
