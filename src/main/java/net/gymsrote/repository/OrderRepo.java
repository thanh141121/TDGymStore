package net.gymsrote.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.order.Order;
import net.gymsrote.entity.user.User;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
	
	Page<Order> findAllByUserAndStatus(User user, EOrderStatus status, Pageable pageable);
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Order o set o.status = net.gymsrote.entity.EnumEntity.EOrderStatus.WAIT_FOR_CONFIRM "
			+ "where o.id = ?1 and o.status = net.gymsrote.entity.EnumEntity.EOrderStatus.WAIT_FOR_PAYMENT")
	int confirmPayment(Long idOrder);

	Page<Order> findAllByUserId(Long idBuyer, Pageable pageable);
}
