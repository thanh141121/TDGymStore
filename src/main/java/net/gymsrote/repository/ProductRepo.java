package net.gymsrote.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.gymsrote.entity.EnumEntity.EProductCategoryStatus;
import net.gymsrote.entity.EnumEntity.EProductStatus;
import net.gymsrote.entity.product.Product;
import net.gymsrote.service.NeedImpl.ProductRepoCustom;

@Repository
public interface ProductRepo
		extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{//, ProductRepoCustom//, RefreshableRepo<Product> {
	
    @Query(value = "SELECT * FROM product WHERE MATCH(name, description) "
            + "AGAINST (?1)", nativeQuery = true)          
    Page<Product> search(String keyword, Pageable pageable);

	Page<Product> findAllByStatus(EProductStatus status, Pageable pageable);
    
    @Transactional
	@Modifying
	@Query(value = "Update Product p set p.status = :status where p.category.id = :idCategory")
	int updateStatusProduct(@Param("idCategory") Long idCategory,@Param("status") EProductStatus status);

	@Transactional
	@Modifying
	@Query(value = "UPDATE product SET nvisit = nvisit + 1 WHERE id = ?1", nativeQuery = true)
	int updateVisitCount(Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE product SET rating1 = rating1 + 1 WHERE id = ?1", nativeQuery = true)
	int updateRating1(Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE product SET rating2 = rating2 + 1 WHERE id = ?1", nativeQuery = true)
	int updateRating2(Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE product SET rating3 = rating3 + 1 WHERE id = ?1", nativeQuery = true)
	int updateRating3(Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE product SET rating4 = rating4 + 1 WHERE id = ?1", nativeQuery = true)
	int updateRating4(Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE product SET rating5 = rating5 + 1 WHERE id = ?1", nativeQuery = true)
	int updateRating5(Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE product SET average_rating = CASE WHEN average_rating = 0 THEN ?2 ELSE (average_rating + ?2) / 2 END WHERE id = ?1",
			nativeQuery = true)
	int updateAverageRating(Long id, Integer point);

	List<Product> findTop10ByStatusOrderByMaxDiscountDesc(EProductStatus status);

	List<Product> findTop10ByStatusOrderByCreatedDateDesc(EProductStatus status);

	List<Product> findTop10ByStatusOrderByNvisitDesc(EProductStatus status);

	List<Product> findTop10ByStatusOrderByNsoldDesc(EProductStatus status);
	// List<Product> findByName(String name);
}
