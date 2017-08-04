package info.chhaileng.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import info.chhaileng.app.model.article.Article;
import info.chhaileng.app.model.filter.ArticleFilter;
import info.chhaileng.app.service.article.ArticleService;
import info.chhaileng.app.service.articlecategory.ArticleCategoryService;
import info.chhaileng.app.service.upload.FileUploadService;
import info.chhaileng.app.service.utility.Paging;

@Controller
public class ArticleController {

	private ArticleService articles;
	private ArticleCategoryService categories;
	private FileUploadService fileUploadService;

	@Autowired
	public void setArticleService(ArticleService a) {
		this.articles = a;
	}
	
	@Autowired
	public void setArticleCategoryService(ArticleCategoryService categories){
		this.categories = categories;
	}
	
	@Autowired
	public void setFileUploadService(FileUploadService f) {
		this.fileUploadService = f;
	}
	
	
	@GetMapping("/")
	public String redirectFromRoot(){
		return "redirect:/article";
	}
	
	@GetMapping("/article")
	public String home(ArticleFilter filter, Paging paging, ModelMap model){
		
		model.addAttribute("categories", categories.getAllCategories());
		model.addAttribute("articles", articles.findAllFilter(filter, paging));
		System.out.println("Paging: " + paging	);
		model.addAttribute("filter", filter);
		model.addAttribute("paging", paging);

		return "index";
	}
	
	// Search by title
//		@RequestMapping(value = { "/article/search" }, method = RequestMethod.GET)
//		public String search(ModelMap m, @ModelAttribute("filter") ArticleFilter filter) {
////			if(filter.getArticle_title().equals("")) filter.setArticle_title(null);;
////			if(filter.getCategory_id()==0) filter.setCategory_id(null);
////			if(filter.getArticle_title()==null && filter.getCategory_id()==null) return "redirect:/article";
//	//
//////			m.addAttribute("articles", articles.search(filter.getArticle_title()));
////			m.addAttribute("articles", articles.filterArticle(filter));
////			m.addAttribute("categories",categories.getAllCategories());
////			m.addAttribute("filter", filter);
//			return "index";
//		}
	
	// home
//	@RequestMapping(value = {"/article"}, method = RequestMethod.GET)
//	public String article(ModelMap m) {
//		m.addAttribute("articles", articles.findAt(1));
//		m.addAttribute("categories",categories.getAllCategories());
//		m.addAttribute("pages", articles.pageList());
//		m.addAttribute("firstPage", articles.pageList().get(0));
//		m.addAttribute("currentPage", 1);
//		m.addAttribute("lastPage", articles.pageList().get(articles.pageList().size()-1));
//		
//		
//		m.addAttribute("filter", new ArticleFilter());
//		m.addAttribute("previousPage", articles.previousPage(1));
//		m.addAttribute("nextPage", articles.nextPage(1));
//		return "index";
//	}
	
	// pagination
//	@RequestMapping(value = {"/article/page/{page}"}, method = RequestMethod.GET)
//	public String articlePage(ModelMap m, @PathVariable("page") Integer page) {
//		m.addAttribute("articles", articles.findAt(page));
//		m.addAttribute("categories",categories.getAllCategories());
//		m.addAttribute("pages", articles.pageList());
//		m.addAttribute("firstPage", articles.pageList().get(0));
//		m.addAttribute("currentPage", page);
//		m.addAttribute("lastPage", articles.pageList().get(articles.pageList().size()-1));
//		
//		m.addAttribute("filter", new ArticleFilter());
//		m.addAttribute("previousPage", articles.previousPage(page));
//		m.addAttribute("nextPage", articles.nextPage(page));
//		System.out.println(articles.pageList());
//		return "index";
//	}
	
	// request to view article with id
	@RequestMapping(value = { "/article/view/{id}" }, method = RequestMethod.GET)
	public String articleView(ModelMap m, @PathVariable("id") Integer id) {
		m.addAttribute("article", articles.findOne(id));
		m.addAttribute("categories",categories.getAllCategories());
		
		m.addAttribute("filter", new ArticleFilter());
		m.addAttribute("previousId", articles.previousArticleId(id));
		m.addAttribute("nextId", articles.nextArticleId(id));
		return "user/view";
	}
	
	// process of removing article
	@RequestMapping(value = { "/remove/article" }, method = RequestMethod.GET)
	public String articleRemove(@RequestParam("id") Integer id) {
		articles.remove(id);
		return "redirect:/article";
	}
	
	// open add new page
	@RequestMapping(value = { "/article/add" }, method = RequestMethod.GET)
	public String add(ModelMap m) {
		m.addAttribute("article", new Article());
		m.addAttribute("categories",categories.getAllCategories());
		m.addAttribute("isAddPage", true);
		m.addAttribute("filter", new ArticleFilter());
		return "admin/addarticle";
	}
	// Open edit page with request id
	@RequestMapping(value = { "/article/edit/{id}" }, method = RequestMethod.GET)
	public String edit(ModelMap m, @PathVariable("id") Integer id){
		m.addAttribute("article", articles.findOne(id));
		m.addAttribute("categories",categories.getAllCategories());
		m.addAttribute("isAddPage", false);
		m.addAttribute("filter", new ArticleFilter());
		return "admin/addarticle";
	}
	
	//Save new article
	@RequestMapping(value="/article/add/save", method=RequestMethod.POST)
	public String saveNewArticle(@RequestParam("file") MultipartFile file, @Valid @ModelAttribute("article") Article article, BindingResult result, ModelMap m) {
		if(result.hasErrors() || file.isEmpty() || !isImage(file.getOriginalFilename())){
			m.addAttribute("article", article);
			m.addAttribute("isAddPage", true);
			m.addAttribute("categories", categories.getAllCategories());
			m.addAttribute("filter", new ArticleFilter());
			return "admin/addarticle";
		}
		
		article.setThumbnail(fileUploadService.upload(file));
		
		// set the right category with id and name
		// article.setArticleCategory(categories.getOneByName(article.getArticleCategory().getCat_name()));
		articles.save(article);
		return "redirect:/article";
	}
	
	// Save when edit article
	@RequestMapping(value="/article/edit/save", method=RequestMethod.POST)
	public String saveChangedUpdate(@RequestParam("file") MultipartFile file, @Valid @ModelAttribute("article") Article article, BindingResult result, ModelMap m) {
		if(result.hasErrors() || !isImage(file.getOriginalFilename())){
			m.addAttribute("article", article);
			m.addAttribute("isAddPage", false);
			m.addAttribute("categories", categories.getAllCategories());
			m.addAttribute("filter", new ArticleFilter());
			return "admin/addarticle";
		}
		
		// pre-set thumbnail prevent file upload is empty
		article.setThumbnail(articles.findOne(article.getId()).getThumbnail());
		if(!file.isEmpty()){
			article.setThumbnail(fileUploadService.upload(file));
		}
		System.out.println(article.getArticleCategory().getCat_id());
		// article.setArticleCategory(categories.getOneByName(article.getArticleCategory().getCat_name()));
		articles.update(article);
		
		return "redirect:/article";
	}
	
	// Filter by category
	@RequestMapping(value = { "/article/category/{category}" }, method = RequestMethod.GET)
	public String filterArticle(ModelMap m, @PathVariable("category") String category) {
		m.addAttribute("articles", articles.findByCategory(category));
		m.addAttribute("categories",categories.getAllCategories());
		m.addAttribute("filter", new ArticleFilter());
		return "index";
	}
	
	// Custom Method
	static boolean isImage(String imageName){
		if(imageName.equals("")) return true; // Prevent error when no file is chosen
		String [] img = {"jpg","png","gif"};
		String reversedName = new StringBuilder(imageName).reverse().toString();
		String reversedExtension = reversedName.substring(0, reversedName.indexOf("."));
		String extenstion = new StringBuilder(reversedExtension).reverse().toString();
		for(String ext : img) {
			if(ext.equals(extenstion)){
				return true;
			}
		}
		return false;
	}

}
