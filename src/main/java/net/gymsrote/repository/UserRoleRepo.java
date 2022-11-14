package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserRole;
import net.gymsrote.entity.user.UserRoleKey;

public interface UserRoleRepo extends JpaRepository<UserRole, UserRoleKey> {
}
