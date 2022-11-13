package net.gymsrote.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.RoleEntity;
import net.gymsrote.entity.user.UserEntity;

public interface IUserService {
	UserEntity saveUser(UserEntity user);
	RoleEntity saveRole(RoleEntity role);
	void addRoleToUser(String username,EUserRole roleName);
	UserEntity getUser(String username);
	List<UserEntity> getUsers();

/*	NewDTO save(NewDTO newDTO);
	void delete(long[] ids);
	List<NewDTO> findAll(Pageable pageable);
	int totalItem();*/
}
