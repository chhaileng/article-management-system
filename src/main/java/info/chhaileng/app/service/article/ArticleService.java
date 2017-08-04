package info.chhaileng.app.service.article;

import java.util.List;

import info.chhaileng.app.model.article.Article;
import info.chhaileng.app.model.filter.ArticleFilter;
import info.chhaileng.app.service.utility.Paging;

public interface ArticleService {
	List<Article> findAll();
	List<Article> findAt(int page_number);
	List<Article> findByCategory(String category);
	List<Article> search(String title);
	Article findOne(int id);
	boolean remove(int id);
	boolean save(Article article);
	boolean update(Article article);
	
	int previousArticleId(int currentId);
	int nextArticleId(int currentId);
	
	// pagination
	List<Integer> pageList();
	int previousPage(int currentId);
	int nextPage(int currentId);
	
	List<Article> filterArticle(ArticleFilter filter);
	
	List<Article> findAllFilter(ArticleFilter filter, Paging paging);
}
