package info.chhaileng.app.model.filter;

public class ArticleFilter {
	private String article_title;
	private Integer category_id;
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	@Override
	public String toString() {
		return "ArticleFilter [article_title=" + article_title + ", category_id=" + category_id + "]";
	}
	public ArticleFilter(String article_title, Integer category_id) {
		super();
		this.article_title = article_title;
		this.category_id = category_id;
	}
	
	public ArticleFilter() {}
	
	
}
