package net.gymsrote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gymsrote.entity.product.ProductImage;

public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
	
}