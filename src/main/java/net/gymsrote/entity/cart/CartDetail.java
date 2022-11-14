package net.gymsrote.entity.cart;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.controller.advice.exception.InvalidInputDataException;
import net.gymsrote.entity.EnumEntity.EProductStatus;
import net.gymsrote.entity.EnumEntity.EProductVariationStatus;
import net.gymsrote.entity.product.ProductVariation;
import net.gymsrote.entity.user.User;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_detail", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "id_product_variation"})})
public class CartDetail {
	
    @EmbeddedId
    private CartDetailKey id;
    
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User buyer;
    
	@MapsId("idProductVariation")
	@ManyToOne
	@JoinColumn(name = "id_product_variation", insertable = false, updatable = false)
	private ProductVariation productVariation;
    
    @Column(name="quantity")
    private Long quantity;
    
	public CartDetail(User buyer, ProductVariation productVariation, Long quantity) {
		this.buyer = buyer;
		this.productVariation = productVariation;
		this.quantity = quantity;
		this.id = new CartDetailKey(buyer.getId(), productVariation.getId());
	}
	
	@PrePersist
	@PreUpdate
	private void check(){
		if (productVariation.getStatus() != EProductVariationStatus.ENABLED) {
			throw new InvalidInputDataException("Product Variation is not enabled");
		}

		if (productVariation.getProduct().getStatus() != EProductStatus.ENABLED) {
			throw new InvalidInputDataException("Product is not enabled");
		}

		if (quantity > productVariation.getAvailableQuantity()) {
			throw new InvalidInputDataException("Quantity cannot be greater than stock");
		}
	}
	
	

}
