package net.gymsrote.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.gymsrote.entity.cart.CartDetail;
import net.gymsrote.entity.cart.CartDetailKey;
import net.gymsrote.entity.user.User;

public interface UserCartDetailRepo extends JpaRepository<CartDetail, CartDetailKey>{
	List<CartDetail> findAllByBuyer(User user, Sort sort);    
	@Query("SELECT cd from CartDetail cd WHERE cd.user =?1")
    Long countByUser(User user);
}
