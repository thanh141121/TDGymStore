package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.gymsrote.entity.user.UserAddress;

@Repository
public interface UserAddressRepo extends JpaRepository<UserAddress, Long> {
}
