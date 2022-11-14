package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.product.ProductImageEntity;

public interface ProductImageRepo extends JpaRepository<ProductImageEntity, Long> {
	
}