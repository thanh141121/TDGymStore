package net.gymsrote.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import net.gymsrote.controller.payload.response.ListResponse;
import net.gymsrote.dto.UserDTO;
import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.UserRole;
import net.gymsrote.entity.user.User;

public interface UserService {
	User saveUser(User user);
	UserRole saveRole(UserRole role);
	void addRoleToUser(String username,EUserRole roleName);
	User getUser(String username);
	ListResponse<UserDTO> getUsers();

/*	NewDTO save(NewDTO newDTO);
	void delete(long[] ids);
	List<NewDTO> findAll(Pageable pageable);
	int totalItem();*/
}
