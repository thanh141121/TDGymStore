package net.gymsrote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserAddress;

@Repository
public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {
}
