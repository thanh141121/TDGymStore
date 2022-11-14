package net.gymsrote.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.Role;
import net.gymsrote.entity.user.User;

public interface IUserService {
	User saveUser(User user);
	Role saveRole(Role role);
	void addRoleToUser(String username,EUserRole roleName);
	User getUser(String username);
	List<User> getUsers();

/*	NewDTO save(NewDTO newDTO);
	void delete(long[] ids);
	List<NewDTO> findAll(Pageable pageable);
	int totalItem();*/
}
