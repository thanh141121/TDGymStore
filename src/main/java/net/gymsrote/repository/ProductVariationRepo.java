package net.gymsrote.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.gymsrote.entity.product.ProductVariation;

public interface ProductVariationRepo extends JpaRepository<ProductVariation, Long> {
	
	//List<ProductVariation> findByProductAndVariationName(Product p, String name);

	@Transactional
	@Modifying
	@Query("UPDATE ProductVariation SET availableQuantity = availableQuantity - :quantity WHERE id = :id AND availableQuantity >= :quantity")
	Integer reduceStock(@Param("id") Long id, @Param("quantity") Long quantity);

	@Transactional
	@Modifying
	@Query("UPDATE ProductVariation SET availableQuantity = availableQuantity + :quantity WHERE id = :id")
	Integer refundStock(@Param("id") Long id, @Param("quantity") Long quantity);
	
}
