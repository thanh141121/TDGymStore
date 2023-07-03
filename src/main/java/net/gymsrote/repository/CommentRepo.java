package net.gymsrote.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.gymsrote.entity.comment.Comment;
import net.gymsrote.entity.product.Product;
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment WHERE product_id = :productID", nativeQuery = true)          
    Page<Comment> findAllOfProduct(@Param("productID") Long productID, Pageable pageable);
    

//    @Query(value = "delete FROM comment WHERE product_id = :productID and user_id = :userId", nativeQuery = true)          
//    void delete(@Param("productID") Long productID, @Param("userId") Long userId);
    
//    default void deleteById(Long productId, Long variantId) {
//        CommentKey id = new CommentKey(productId, variantId);
//        deleteById(id);
//    }

}
