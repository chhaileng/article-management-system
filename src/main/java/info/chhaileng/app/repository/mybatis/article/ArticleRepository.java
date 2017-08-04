package info.chhaileng.app.repository.mybatis.article;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import info.chhaileng.app.model.article.Article;
import info.chhaileng.app.model.filter.ArticleFilter;
import info.chhaileng.app.repository.mybatis.provider.ArticleProvider;
import info.chhaileng.app.service.utility.Paging;

@Repository
public interface ArticleRepository {
	@Select("SELECT a.id, a.title, a.description, a.thumbnail, a.date, a.author, "
			+ "c.category_id, c.category_name FROM tb_article AS a "
			+ "INNER JOIN tb_category AS c ON a.category_id = c.category_id ORDER BY a.id DESC")
	@Results({
			@Result(property="articleCategory.cat_id", column="category_id"),
			@Result(property="articleCategory.cat_name", column="category_name")
	})
	List<Article> findAll();
	
	// ========================================= //
	@Select("SELECT a.id, a.title, a.description, a.thumbnail, a.date, a.author, "
			+ "c.category_id, c.category_name FROM tb_article AS a "
			+ "INNER JOIN tb_category AS c ON a.category_id = c.category_id ORDER BY a.id DESC "
			+ "LIMIT 6 OFFSET (#{page_number}-1)*6")
	@Results({
			@Result(property="articleCategory.cat_id", column="category_id"),
			@Result(property="articleCategory.cat_name", column="category_name")
	})
	List<Article> findAt(int page_number);
	
	// ========================================= //
	@Select("SELECT a.id, a.title, a.description, a.thumbnail, a.date, a.author, "
			+ "c.category_id, c.category_name FROM tb_article AS a "
			+ "INNER JOIN tb_category AS c ON a.category_id = c.category_id "
			+ "WHERE c.category_name = #{articleCategory.cat_name} ORDER BY a.id DESC")
	@Results({
			@Result(property="articleCategory.cat_id", column="category_id"),
			@Result(property="articleCategory.cat_name", column="category_name")	
	})
	List<Article> findByCategory(String category);
	
	// ========================================= //
	@Select("SELECT a.id, a.title, a.description, a.thumbnail, a.date, a.author, "
			+ "c.category_id, c.category_name FROM tb_article AS a "
			+ "INNER JOIN tb_category AS c ON a.category_id = c.category_id "
			+ "WHERE LOWER(a.title) LIKE '%' || LOWER(#{search}) || '%' ORDER BY a.id DESC")
	@Results({
			@Result(property="articleCategory.cat_id", column="category_id"),
			@Result(property="articleCategory.cat_name", column="category_name")	
	})
	List<Article> search(@Param("search") String title);
	
	// ========================================= //
	@Select("SELECT a.id, a.title, a.description, a.thumbnail, a.date, a.author, "
			+ "c.category_id, c.category_name FROM tb_article AS a "
			+ "INNER JOIN tb_category AS c ON a.category_id = c.category_id WHERE a.id=#{id}")
	@Results({
			@Result(property="articleCategory.cat_id", column="category_id"),
			@Result(property="articleCategory.cat_name", column="category_name")
	})
	Article findOne(int id);
	
	// ========================================= //
	@Delete("DELETE FROM tb_article WHERE id=#{id}")
	boolean remove(int id);
	
	// ========================================= //
	@Insert("INSERT INTO tb_article (title, description, thumbnail, date, author, category_id) VALUES("
			+ "#{title}, #{description}, #{thumbnail}, #{date}, #{author}, #{articleCategory.cat_id})")
	boolean save(Article article);
	
	// ========================================= //
	@Update("UPDATE tb_article SET "
			+ "title = #{title}, "
			+ "description = #{description}, "
			+ "thumbnail = #{thumbnail}, "
			+ "date = #{date}, "
			+ "author = #{author}, "
			+ "category_id = #{articleCategory.cat_id} "
			+ "WHERE id = #{id}")
	boolean update(Article article);
	
	// ========================================= //
	@Select("SELECT id FROM tb_article WHERE category_id IN (SELECT category_id FROM tb_category) ORDER BY id")
	List<Integer> getAllId();
	
	// ========================================= //
	@Select("SELECT COUNT(*) FROM tb_article WHERE category_id IN (SELECT category_id FROM tb_category)")
	int articleCount();
	
	@SelectProvider(method="findAllFiltered" ,type=ArticleProvider.class)
	@Results({
		@Result(property="articleCategory.cat_id", column="category_id"),
		@Result(property="articleCategory.cat_name", column="category_name")
	})
	List<Article> filterArticle(ArticleFilter filter);
	
	@SelectProvider(method = "findAllFilter", type = ArticleProvider.class)
	@Results({
		@Result(property="articleCategory.cat_id", column="category_id"),
		@Result(property="articleCategory.cat_name", column="category_name")
	})
	List<Article> findAllFilter(@Param("filter") ArticleFilter filter, @Param("paging") Paging paging);
	
	@SelectProvider(method = "countAllFilter", type = ArticleProvider.class)
	Integer countAllFilter(@Param("filter") ArticleFilter filter);
	
}
