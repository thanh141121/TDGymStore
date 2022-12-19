package net.gymsrote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.controller.payload.response.BaseResponse;
import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.dto.UserAddressDTO;
import net.gymsrote.dto.UserDTO;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserAddress;
import net.gymsrote.entity.user.UserRole;
import net.gymsrote.repository.RoleRepository;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.utils.ServiceUtils;

@Service
@Transactional
@Slf4j
public class UserService{// implements IUserService{
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ServiceUtils serviceUtils;
	
	
	public BaseResponse isEnabled(Long id, boolean isEnable) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with id: " + id));
		user.setIsEnabled(isEnable);
		return new BaseResponse(true, "Change successfully!");
	}

	public User saveUser(User user) {
		log.info("Saving user {} to the database", user.getFullname());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	public UserRole saveRole(UserRole role) {
		log.info("Saving new role {} to the database", role.getName());
		return roleRepo.save(role);
	}

	public void addRoleToUser(String username, EUserRole roleName) {
		log.info("Adding role {} to user", roleName, username);
		User user = userRepo.findByUsernameOrEmail(username, username)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username or email : " + username));
		UserRole role = roleRepo.findByName(roleName);
		user.setRole(role);
	}

	public User getUser(String username) {
		log.info("Fetching user {}", username);
		return userRepo.findByUsernameOrEmail(username, username)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username or email : " + username));
	}


	public ListResponse<UserDTO> getUsers() {
		log.info("Fetching all users");
		return serviceUtils.convertToListResponse(userRepo.findAll(), UserDTO.class);
				//userRepo.findAll();
	}


}
