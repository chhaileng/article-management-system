package info.chhaileng.app.service.articlecategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.chhaileng.app.model.article.ArticleCategory;
import info.chhaileng.app.repository.mybatis.articlecategory.ArticleCategoryRepo;

@Service
public class ArticleCategoryServiceImp implements ArticleCategoryService {

	private ArticleCategoryRepo articleCategoryRepo;
	
	@Autowired
	void setArticleCategoryReop(ArticleCategoryRepo articleCategoryReop){
		this.articleCategoryRepo = articleCategoryReop;
	}
	
	@Override
	public List<ArticleCategory> getAllCategories() {
		return articleCategoryRepo.getAllCategories();
	}
	
	@Override
	public ArticleCategory getOneById(int id){
		return articleCategoryRepo.getOneById(id);
	}
	
	@Override
	public ArticleCategory getOneByName(String category_name) {
		return articleCategoryRepo.getOneByName(category_name);
	}

	@Override
	public boolean add(ArticleCategory category) {
		return articleCategoryRepo.add(category);
	}

	@Override
	public boolean edit(ArticleCategory category) {
		return articleCategoryRepo.edit(category);
	}

	@Override
	public boolean remove(int id) {
		return articleCategoryRepo.remove(id);
	}

}
