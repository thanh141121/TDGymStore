package net.gymsrote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    List<UserEntity> findByIdIn(List<Long> userIds);

    UserEntity findByUsername(String username);

    UserEntity findByUsernameAndPassword(String username, String password);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
