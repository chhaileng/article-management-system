package info.chhaileng.app.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import info.chhaileng.app.model.security.Role;
import info.chhaileng.app.model.security.User;
import info.chhaileng.app.repository.mybatis.user.UserRepository;
import info.chhaileng.app.service.upload.FileUploadService;

@Controller
public class UserController {
	private UserRepository usrRepo;
	private FileUploadService fileUploadService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public void setUsrRepo(UserRepository usrRepo) {
		this.usrRepo = usrRepo;
	}
	@Autowired
	public void setfileUploadService(FileUploadService f) {
		this.fileUploadService = f;
	}
	@Autowired
	public void setBCryptionPasswordEncoder(BCryptPasswordEncoder bc) {
		this.bCryptPasswordEncoder = bc;
	}

	@GetMapping("/profile")
	public String profile(Model m, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
        String currentUser = principal.getName();
		m.addAttribute("myUser", usrRepo.loadUserByUsername(currentUser));
		m.addAttribute("users", usrRepo.findAllUsers());
		m.addAttribute("newUser", new User());
		m.addAttribute("adminMode", false);
		m.addAttribute("isEditPage", false);
		
		m.addAttribute("userRoleIsAdmin", false);
		m.addAttribute("userStatus", true);
		
		return "user/userprofile";
	}
	
	@GetMapping("/profile/user/edit/{id}")
	public String edit_user(@PathVariable("id") Integer id, ModelMap m, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
        String currentUser = principal.getName();
		m.addAttribute("myUser", usrRepo.loadUserByUsername(currentUser));
		m.addAttribute("users", usrRepo.findAllUsers());
		m.addAttribute("newUser", usrRepo.loadUserById(id));
		m.addAttribute("adminMode", true);
		m.addAttribute("isEditPage", true);
		boolean userRoleIsAdmin = usrRepo.loadUserById(id).getRoles().get(0).getRole().equals("ADMIN")?true:false;
		m.addAttribute("userRoleIsAdmin", userRoleIsAdmin);
		m.addAttribute("userStatus", usrRepo.loadUserById(id).isStatus());
		return "user/userprofile";
	}
	
	@PostMapping("/profile/user/edit/save")
	public String save_user_edited(@RequestParam("radioRole") String role, @RequestParam("radioStatus") boolean status, @RequestParam("file1") MultipartFile file, @ModelAttribute("newUser") User newUser, HttpServletRequest request) {
		newUser.setStatus(status);
		if(newUser.getPassword().equals(""))
			newUser.setPassword(usrRepo.loadUserById(newUser.getId()).getPassword());
		else
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
		if(file.isEmpty())
			newUser.setPhoto(usrRepo.loadUserById(newUser.getId()).getPhoto());
		else
			newUser.setPhoto("/photo/" + file.getOriginalFilename());
		
		System.out.println("ROLE   : " + role);
		
		
		System.out.println("USER   : " + newUser);
		
		return "user/msg/user_edit_changed";
	}
	
	@PostMapping("/profile/save/information")
	public String save_information(@RequestParam("file") MultipartFile file, @ModelAttribute("myUser") User myUser, ModelMap m, HttpServletRequest request) {
		if(myUser.getFullname().equals("")) {
			m.addAttribute("me", myUser);
			return "user/msg/infonotchanged";
		}
		if(!file.isEmpty()) {
			myUser.setPhoto(fileUploadService.upload(file));
		} else {
			myUser.setPhoto(usrRepo.loadUserByUsername(request.getUserPrincipal().getName()).getPhoto());
		}
		myUser.setId(usrRepo.loadUserByUsername(request.getUserPrincipal().getName()).getId());
		usrRepo.updateInformation(myUser);
		return "user/msg/infochanged";
	}
	@PostMapping("/profile/save/password")
	public String save_password(@RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd, @RequestParam("confirmPwd") String confirmPwd, HttpServletRequest request) {
		boolean oldPwdIsCorrect = bCryptPasswordEncoder.matches(oldPwd, usrRepo.loadUserByUsername(request.getUserPrincipal().getName()).getPassword());
		boolean newPwdIsMatch = newPwd.equals(confirmPwd);
		if(oldPwdIsCorrect && newPwdIsMatch) {
			newPwd = bCryptPasswordEncoder.encode(newPwd);
			User user = new User();
			user.setId(usrRepo.loadUserByUsername(request.getUserPrincipal().getName()).getId());
			user.setPassword(newPwd);
			usrRepo.updatePassword(user);
			return "user/msg/pwdchanged";
		}
		return "user/msg/pwdnotchanged";
	}
	
	@PostMapping("/profile/save/newuser")
	public String save_new_user(@RequestParam("radioRole") String role, @RequestParam("radioStatus") boolean status, @RequestParam("file1") MultipartFile file, @ModelAttribute("newUser") User newUser, HttpServletRequest request) {
	
		if(newUser.getFullname().equals("") || newUser.getFullname().equals("") || newUser.getPassword().equals(""))
			return "user/msg/usernotadded";
		
		List<Role> roles = new ArrayList<>();	Role r = new Role();
		r.setId(2);	roles.add(r);
		
		if(role.equals("Admin"))	r.setId(1);	roles.add(r);
		
		newUser.setRoles(roles);
		newUser.setPhoto(fileUploadService.upload(file));
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
		newUser.setStatus(status);
		
		usrRepo.addUser(newUser);
		// get its real id from db and set to newUser object
		newUser.setId(usrRepo.loadUserByUsername(newUser.getUsername()).getId());
		
		usrRepo.addUserRole(newUser.getId(), 2);
		if(role.equals("Admin"))
			usrRepo.addUserRole(newUser.getId(), 1);
		System.out.println(newUser);
		return "user/msg/useradded";
		
	}
	
	@GetMapping("/profile/admin/remove_user/{id}")
	public String removeCategory(@PathVariable("id") Integer id, HttpServletRequest request){
		if(id!=usrRepo.loadUserByUsername(request.getUserPrincipal().getName()).getId()) {
			usrRepo.remove(id);
			return "/user/msg/userdeleted";
		}
		return "/user/msg/usernotdeleted";
	}
}
