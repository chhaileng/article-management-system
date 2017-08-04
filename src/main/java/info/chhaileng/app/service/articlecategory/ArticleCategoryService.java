package info.chhaileng.app.service.articlecategory;

import java.util.List;

import info.chhaileng.app.model.article.ArticleCategory;

public interface ArticleCategoryService {
	List<ArticleCategory> getAllCategories();
	ArticleCategory getOneById(int id);
	ArticleCategory getOneByName(String category_name);
	boolean add(ArticleCategory category);
	boolean edit(ArticleCategory category);
	boolean remove(int id);
}
