package net.gymsrote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import net.gymsrote.entity.address.District;
import net.gymsrote.entity.address.Province;
import net.gymsrote.entity.address.Ward;
import net.gymsrote.entity.user.User;
import net.gymsrote.entity.user.UserAddress;

@Repository
public interface AddressWardRepo extends JpaRepository<Ward, Long> {
	Ward findByWardCode(Long id);
}
