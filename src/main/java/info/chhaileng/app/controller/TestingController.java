package info.chhaileng.app.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import info.chhaileng.app.service.upload.FileUploadService;

@Controller
public class TestingController {
	
	@Autowired
	private BCryptPasswordEncoder bc;

	@Autowired
	private FileUploadService fileUploadService;
	
	@ResponseBody
	@GetMapping("/encrypt")
	public String pwd(String pwd) {
		return bc.encode(pwd);
	}
	
	@GetMapping("/upload")
	public String upload() {
		return "fileupload";
	}
	
	@PostMapping("/upload/save")
	public String saveUpload(@RequestParam("file") MultipartFile file){
		System.out.println(fileUploadService.upload(file));
		return "redirect:/upload";
	}
	
	@GetMapping(value = "/currentUser")
	@ResponseBody
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }
}
