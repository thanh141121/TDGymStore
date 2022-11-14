package net.gymsrote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(EUserRole roleName);
}
