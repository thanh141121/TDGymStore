package net.gymsrote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.gymsrote.entity.product.ProductCategory;

@Repository
public interface ProductCategoryRepo
		extends JpaRepository<ProductCategory, Long>{
	Boolean existsByName(String name);

	// @Query(value = "SELECT pc FROM ProductCategory pc WHERE pc.parent IS NULL AND
	// pc.status <> net.gymsrote.entity.EnumEntity.EProductCategoryStatus.BANNED")
	@Query(value = "SELECT pc FROM ProductCategory pc WHERE pc.status <> net.gymsrote.entity.EnumEntity.EProductCategoryStatus.DISABLED")
	List<ProductCategory> findAllCategories();
}
