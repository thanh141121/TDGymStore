package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.user.UserEntity;
import net.gymsrote.entity.user.UserRoleEntity;
import net.gymsrote.entity.user.UserRoleKey;

public interface UserRoleRepo extends JpaRepository<UserRoleEntity, UserRoleKey> {}
