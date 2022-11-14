package net.gymsrote.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.RoleEntity;
import net.gymsrote.entity.user.UserEntity;
import net.gymsrote.entity.user.UserRoleEntity;
import net.gymsrote.repository.RoleRepository;
import net.gymsrote.repository.UserRepository;
import net.gymsrote.repository.UserRoleRepo;
import net.gymsrote.service.IUserService;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
@Slf4j
public class UserService implements IUserService, UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserEntity saveUser(UserEntity user) {
		log.info("Saving user {} to the database", user.getFullname());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public RoleEntity saveRole(RoleEntity role) {
		log.info("Saving new role {} to the database", role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, EUserRole roleName) {
		log.info("Adding role {} to user", roleName, username);
		UserEntity user = userRepo.findByUsernameOrEmail(username, username)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username or email : " + username));
		RoleEntity role = roleRepo.findByName(roleName);
		UserRoleEntity userRole = new UserRoleEntity(user, role);
		userRoleRepo.save(userRole);
		// user.getUserRoles().add(new UserRoleEntity(user, role));
		// user.getRoles().add(role);
	}

	@Override
	public UserEntity getUser(String username) {
		log.info("Fetching user {}", username);
		return userRepo.findByUsernameOrEmail(username, username)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username or email : " + username));
	}

	@Override
	public List<UserEntity> getUsers() {
		log.info("Fetching all users");
		return userRepo.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepo.findByUsername(username);
		if (user == null) {
			log.error("User not found in the databse");
			throw new UsernameNotFoundException("User not found in the databse");
		} else {
			log.info("User found in the database {}", username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getUserRoles().forEach(uRole -> {
			authorities.add(new SimpleGrantedAuthority(uRole.getRoles().getName().name()));
		});
		// user.getRoles().forEach(role -> {
		// authorities.add(new SimpleGrantedAuthority(role.getName().name()));
		// });
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(), authorities);
	}
}
