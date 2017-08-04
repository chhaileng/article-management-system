package info.chhaileng.app.repository.mybatis.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import info.chhaileng.app.model.filter.ArticleFilter;
import info.chhaileng.app.service.utility.Paging;

public class ArticleProvider {
	public String findAllFiltered(ArticleFilter filter) {
		System.out.println(filter);
		return new SQL() {{
			SELECT("a.id, a.title, a.description, a.thumbnail");
			SELECT("a.date, a.author, c.category_id, c.category_name");
			FROM("tb_article a");
			INNER_JOIN("tb_category c ON a.category_id = c.category_id");
			if(filter.getArticle_title()!=null)
				WHERE("a.title ILIKE '%' || #{article_title} || '%'");
			if(filter.getCategory_id()!=null)
				WHERE("c.category_id = #{category_id}");
			ORDER_BY("a.id DESC");
//			ORDER_BY("a.id DESC LIMIT 6 OFFSET 0");
		}}.toString();
	}
	
	public String findAllFilter(@Param("filter") ArticleFilter filter, @Param("paging") Paging paging){
		
		System.out.println("Filter" + filter);
		
		return new SQL(){{
			SELECT("a.id, a.title, a.description, a.thumbnail");
			SELECT("a.date, a.author, c.category_id, c.category_name");
			FROM("tb_article a");
			LEFT_OUTER_JOIN("tb_category c ON a.category_id = c.category_id");
			
			if(filter.getCategory_id()!=null)
				WHERE("a.category_id = #{filter.category_id}");
			
			if(filter.getArticle_title()!=null)
				WHERE("A.title ILIKE '%' || #{filter.article_title} || '%'");
			
			ORDER_BY("A.id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}");
			
		}}.toString();
	}
	
	public String countAllFilter(@Param("filter") ArticleFilter filter){
		return new SQL(){{
			SELECT("COUNT(A.id)");
			FROM("tb_article A");
			
			if(filter.getCategory_id()!=null)
				WHERE("A.category_id = #{filter.category_id}");
			
			if(filter.getArticle_title()!=null)
				WHERE("A.title ILIKE '%' || #{filter.article_title} || '%'");
			
		}}.toString();
	}
	
}
