package info.chhaileng.app.model.article;

import org.hibernate.validator.constraints.NotBlank;

public class Article {
	private int id;
	@NotBlank
	private String title;
	@NotBlank
	private String description;
	// @NotBlank
	private String thumbnail;
	@NotBlank
	private String date;
	@NotBlank
	private String author;
	private ArticleCategory articleCategory;
	

	public Article() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", description=" + description + ", thumbnail=" + thumbnail
				+ ", date=" + date + ", author=" + author + ", category=" + articleCategory.toString() + "]";
	}
}
