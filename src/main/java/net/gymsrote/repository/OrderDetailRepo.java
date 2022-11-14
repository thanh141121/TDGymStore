package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepo {//extends JpaRepository<OrderDetail, OrderDetailPK> {
	//boolean existsByOrderAndReviewed(Order order, boolean reviewed);
}
