package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.gymsrote.entity.order.OrderDetail;
import net.gymsrote.entity.order.OrderDetailKey;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, OrderDetailKey> {
	//boolean existsByOrderAndReviewed(Order order, boolean reviewed);
}
