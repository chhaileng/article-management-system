package info.chhaileng.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import info.chhaileng.app.model.article.ArticleCategory;
import info.chhaileng.app.model.filter.ArticleFilter;
import info.chhaileng.app.service.articlecategory.ArticleCategoryService;

@Controller
public class CategoryController {
	private ArticleCategoryService categories;
	
	@Autowired
	public void setArticleCategoryService(ArticleCategoryService categories){
		this.categories = categories;
	}
	
	//Category
	@RequestMapping(value = { "/article/category" }, method = RequestMethod.GET)
	public String category(ModelMap m) {
		m.addAttribute("category", new ArticleCategory());
		m.addAttribute("categories",categories.getAllCategories());
		m.addAttribute("isAddCat", true);
		m.addAttribute("filter", new ArticleFilter());
		return "admin/category";
	}
	//Save new category
	@PostMapping("/article/category/save")
	public String saveNewCategory(ModelMap m, @ModelAttribute("category") ArticleCategory category) {
		categories.add(category);
		return "redirect:/article/category";
	}
	//Remove
	@GetMapping("/article/category/remove")
	public String removeCategory(@RequestParam("id") Integer id){
		categories.remove(id);
		return "redirect:/article/category";
	}
	//Edit
	@GetMapping("/article/category/edit/{id}")
	public String editCategory(ModelMap m, @PathVariable("id") Integer id){
		m.addAttribute("category", categories.getOneById(id));
		m.addAttribute("categories",categories.getAllCategories());
		m.addAttribute("isAddCat", false);
		m.addAttribute("filter", new ArticleFilter());
		return "admin/category";
	}
	//Save edit category
	@PostMapping("/article/category/edit/save")
	public String saveEditCategory(ModelMap m, @ModelAttribute("category") ArticleCategory category) {
		categories.edit(category);
		return "redirect:/article/category";
	}
}
