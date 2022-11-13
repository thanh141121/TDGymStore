package net.gymsrote.entity.cart;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.product.ProductEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_detail", uniqueConstraints = {@UniqueConstraint(columnNames = {"cart_id", "product_id"})})
public class CartDetailEntity {
	
    @EmbeddedId
    private CartDetailKey id;
    
    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    CartEntity cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    ProductEntity product;
    
    @Column(name="quantity")
    private Long quantity;
	
	

}
