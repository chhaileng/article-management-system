package info.chhaileng.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import info.chhaileng.app.model.article.Article;
import info.chhaileng.app.model.filter.ArticleFilter;
import info.chhaileng.app.service.article.ArticleService;

@RestController
public class FilterController {
	@Autowired
	private ArticleService aService;
	
	@GetMapping("/test")
	public List<Article> filterArticle(ArticleFilter filter) {
		return aService.filterArticle(filter);
	}
}
