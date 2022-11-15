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
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.dto.UserDTO;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.UserRole;
import net.gymsrote.entity.user.User;
import net.gymsrote.repository.RoleRepository;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.UserService;
import net.gymsrote.service.utils.ServiceUtils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ServiceUtils serviceUtils;

	@Override
	public User saveUser(User user) {
		log.info("Saving user {} to the database", user.getFullname());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public UserRole saveRole(UserRole role) {
		log.info("Saving new role {} to the database", role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, EUserRole roleName) {
		log.info("Adding role {} to user", roleName, username);
		User user = userRepo.findByUsernameOrEmail(username, username)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username or email : " + username));
		UserRole role = roleRepo.findByName(roleName);
		// UserRole userRole = new UserRole(user, role);
		// userRoleRepo.save(userRole);
		// user.getUserRoles().add(new UserRoleEntity(user, role));
		// user.getRoles().add(role);
	}

	@Override
	public User getUser(String username) {
		log.info("Fetching user {}", username);
		return userRepo.findByUsernameOrEmail(username, username)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username or email : " + username));
	}

//	@Override
//	public List<User> getUsers() {
//		log.info("Fetching all users");
//		return userRepo.findAll();
//	}
	@Override
	public ListResponse<UserDTO> getUsers() {
		log.info("Fetching all users");
		return serviceUtils.convertToListResponse(userRepo.findAll(), UserDTO.class);
				//userRepo.findAll();
	}


}
