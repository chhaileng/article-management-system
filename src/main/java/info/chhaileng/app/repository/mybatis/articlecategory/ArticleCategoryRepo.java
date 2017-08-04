package info.chhaileng.app.repository.mybatis.articlecategory;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import info.chhaileng.app.model.article.ArticleCategory;

@Repository
public interface ArticleCategoryRepo {
	@Select("SELECT category_id, category_name FROM tb_category ORDER BY category_id")
	@Results({
		@Result(property="cat_id", column="category_id"),
		@Result(property="cat_name", column="category_name")
	})
	List<ArticleCategory> getAllCategories();
	
	@Select("SELECT category_id, category_name FROM tb_category WHERE category_id=#{id}")
	@Results({
		@Result(property="cat_id", column="category_id"),
		@Result(property="cat_name", column="category_name")
	})
	ArticleCategory getOneById(int id);
	
	@Select("SELECT category_id, category_name FROM tb_category WHERE category_name = #{category_name}")
	@Results({
		@Result(property="cat_id", column="category_id"),
		@Result(property="cat_name", column="category_name")
	})
	ArticleCategory getOneByName(String category_name);
	
	@Insert("INSERT INTO tb_category (category_name) VALUES (#{cat_name})")
	boolean add(ArticleCategory category);
	
	@Update("UPDATE tb_category SET category_name = #{cat_name} WHERE category_id = #{cat_id}")
	boolean edit(ArticleCategory category);
	
	@Delete("DELETE FROM tb_category WHERE category_id = #{id}")
	boolean remove(int id);
}
