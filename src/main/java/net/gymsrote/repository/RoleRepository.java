package net.gymsrote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.EnumEntity.EUserRole;
import net.gymsrote.entity.user.UserEntity;
import net.gymsrote.entity.user.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	RoleEntity findByName(EUserRole roleName);
}
