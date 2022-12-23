package net.gymsrote.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.gymsrote.entity.cart.CartDetail;
import net.gymsrote.entity.cart.CartDetailKey;
import net.gymsrote.entity.user.User;

@Repository
public interface UserCartDetailRepo extends JpaRepository<CartDetail, CartDetailKey>{
	List<CartDetail> findAllByBuyer(User user, Sort sort); 
	  
	@Query("SELECT sum(cd.quantity) FROM CartDetail cd WHERE cd.buyer = ?1")
    Long countByUser(User user);
}
