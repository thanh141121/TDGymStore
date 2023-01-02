package net.gymsrote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.gymsrote.controller.payload.response.BaseResponse;
import net.gymsrote.controller.payload.response.DataResponse;
import net.gymsrote.controller.payload.response.ListWithPagingResponse;
import net.gymsrote.dto.OrderDTO;
import net.gymsrote.dto.UserDTO;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserRole;
import net.gymsrote.repository.RoleRepository;
import net.gymsrote.repository.UserRepo;
import net.gymsrote.service.utils.ServiceUtils;
import net.gymsrote.utility.PagingInfo;

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
	
	public void updateStatus(Long id, Boolean status) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with id: " + id));
		user.setIsEnabled(status);
		
	}
	
	
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
		return userRepo.findByUsernameOrEmail(username, username)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username or email : " + username));
	}

	public DataResponse<UserDTO> getById(Long id) {
		return serviceUtils.convertToDataResponse(
				userRepo.getReferenceById(id),
				UserDTO.class
			);
	}

	public DataResponse<UserDTO> getUserProfile(String username) {
		User user = userRepo.findByUsernameOrEmail(username, username)
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username or email : " + username));
		return serviceUtils.convertToDataResponse(user, UserDTO.class);
	}


	public ListWithPagingResponse<?> getAll(PagingInfo pagingInfo) {
		return serviceUtils.convertToListResponse(userRepo.findAll(pagingInfo.getPageable()),
				UserDTO.class);
	}
}
