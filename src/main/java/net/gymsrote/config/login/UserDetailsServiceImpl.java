package net.gymsrote.config.login;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.impl.UserServiceImpl;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			log.error("User not found in the databse");
			throw new UsernameNotFoundException("User not found in the databse");
		} else {
			log.info("User found in the database {}", username);
		}
		// Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		// user.getUserRoles().forEach(uRole -> {
		// 	authorities.add(new SimpleGrantedAuthority(uRole.getRoles().getName().name()));
		// });
		return new UserDetailsImpl<User>(user);
		// user.getRoles().forEach(role -> {
		// authorities.add(new SimpleGrantedAuthority(role.getName().name()));
		// });
//		return new org.springframework.security.core.userdetails.User(user.getUsername(),
//				user.getPassword(), authorities);
	}
}
