package net.gymsrote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
	UserRole findByName(EUserRole roleName);
}
