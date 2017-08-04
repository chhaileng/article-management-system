package info.chhaileng.app.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import info.chhaileng.app.model.security.User;
import info.chhaileng.app.repository.mybatis.user.UserRepository;

@Service
public class UserDetailsServiceImplement implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.loadUserByUsername(username);
		return user;
	}
	
}
