package net.gymsrote.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepo{// extends JpaRepository<Order, Long>, OrderRepoCustom {
//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE Order o set o.status = net.gymsrote.entity.EnumEntity.EOrderStatus.WAIT_FOR_CONFIRM "
//			+ "where o.id = ?1 and o.status = net.gymsrote.entity.EnumEntity.EOrderStatus.WAIT_FOR_PAYMENT")
//	int confirmPayment(Long idOrder);
//
//	List<Order> findAllByBuyerId(Long idBuyer, Sort sort);
}
