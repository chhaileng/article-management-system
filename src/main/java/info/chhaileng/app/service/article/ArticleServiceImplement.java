package info.chhaileng.app.service.article;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.chhaileng.app.model.article.Article;
import info.chhaileng.app.model.filter.ArticleFilter;
import info.chhaileng.app.repository.mybatis.article.ArticleRepository;
import info.chhaileng.app.service.utility.Paging;

@Service
public class ArticleServiceImplement implements ArticleService {

	private ArticleRepository articles;
	
	@Autowired
	public ArticleServiceImplement(ArticleRepository articles) {
		this.articles = articles;
	}

	@Override
	public List<Article> findAll() {
		return articles.findAll();
	}
	
	@Override
	public List<Article> findAt(int page_number) {
		return articles.findAt(page_number);
	}
	
	@Override
	public List<Article> findByCategory(String category) {
		return articles.findByCategory(category);
	}
	@Override
	public List<Article> search(String title) {
		return articles.search(title);
	}

	@Override
	public Article findOne(int id) {
		return articles.findOne(id);
	}

	@Override
	public boolean remove(int id) {
		return articles.remove(id);
	}

	@Override
	public boolean save(Article article) {
		return articles.save(article);
	}
	
	@Override
	public boolean update(Article article) {
		return articles.update(article);
	}
	
	@Override
	public int previousArticleId(int currentId) {
		List<Integer> lstId = articles.getAllId();
		for (int i=0 ; i< lstId.size() ; i++) {
			if (i==0 && lstId.get(i)==currentId)	return 0;
			if (lstId.get(i)==currentId)	return lstId.get(i-1);
		}
		return 0;
	}
	
	@Override
	public int nextArticleId(int currentId) {
		List<Integer> lstId = articles.getAllId();
		for (int i=0 ; i< lstId.size() ; i++) {
			if (i==lstId.size()-1 && lstId.get(i)==currentId)	return 0;
			if (lstId.get(i)==currentId)	return lstId.get(i+1);
		}
		return 0;
	}
	
	@Override
	public List<Integer> pageList() {
		List<Integer> list = new ArrayList<>();
		int page = articles.articleCount() / 6;
		if((articles.articleCount() % 6) != 0)
			page++;
		for(int i=1 ; i<=page ; i++)
			list.add(i);
		return list;
	}
	
	@Override
	public int previousPage(int currentPage) {
		for (int i=0 ; i<this.pageList().size() ; i++) {
			if (i==0 && currentPage==this.pageList().get(i))	return 0;
			if (currentPage==this.pageList().get(i)) return this.pageList().get(i-1);
		}
		return 0;
	}
	
	@Override
	public int nextPage(int currentPage) {
		for (int i=0 ; i<this.pageList().size() ; i++) {
			if (i==this.pageList().size()-1 && currentPage==this.pageList().get(i)) return 0;
			if (currentPage==this.pageList().get(i)) return this.pageList().get(i+1);
		}
		return 0;
	}
	
	public List<Article> filterArticle(ArticleFilter filter) {
		return articles.filterArticle(filter);
	}

	@Override
	public List<Article> findAllFilter(ArticleFilter filter, Paging paging) {
		paging.setTotalCount(articles.countAllFilter(filter));
		return articles.findAllFilter(filter, paging);
	}

}
