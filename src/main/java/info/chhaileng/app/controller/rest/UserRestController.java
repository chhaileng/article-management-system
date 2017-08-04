package info.chhaileng.app.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import info.chhaileng.app.model.security.Role;
import info.chhaileng.app.model.security.User;
import info.chhaileng.app.repository.mybatis.user.UserRepository;

@RestController
public class UserRestController {
	
	@Autowired
	private UserRepository usrRepo;
	
	@GetMapping("/users")
	public List<User> getAllUser() {
		return usrRepo.findAllUsers();
	}
	@PostMapping("/users")
	public boolean addNewUser(@RequestBody User user) {
		usrRepo.addUser(user);
		
		List<Role> roles = new ArrayList<>();
		Role r = new Role(); r.setRole(user.getRoles().get(0).getRole()); r.setId(user.getRoles().get(0).getRole().equals("Admin")?1:2);
		roles.add(r);
		
		user.setId(usrRepo.loadUserByUsername(user.getUsername()).getId());
		usrRepo.addUserRole(user.getId(), r.getId());
		
		return false;
	}
	
}
