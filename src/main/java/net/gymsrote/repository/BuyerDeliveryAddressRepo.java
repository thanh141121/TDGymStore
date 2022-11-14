package net.gymsrote.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.user.buyer.Buyer;
import net.gymsrote.entity.user.buyer.BuyerDeliveryAddress;

public interface BuyerDeliveryAddressRepo extends JpaRepository<BuyerDeliveryAddress, Long> {
    List<BuyerDeliveryAddress> findAllByBuyer(Buyer buyer, Sort sort);
}
