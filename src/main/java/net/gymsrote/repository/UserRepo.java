package net.gymsrote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import net.gymsrote.entity.user.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    @Nullable
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

	User findByVerificationCode(String verificationCode);
}
