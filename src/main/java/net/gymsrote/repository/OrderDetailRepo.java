package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.gymsrote.entity.order.Order;
import net.gymsrote.entity.order.OrderDetail;
import net.gymsrote.entity.order.OrderDetailPK;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, OrderDetailPK> {
	boolean existsByOrderAndReviewed(Order order, boolean reviewed);
}
