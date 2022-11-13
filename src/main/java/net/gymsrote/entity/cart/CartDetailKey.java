package net.gymsrote.entity.cart;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class CartDetailKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8283881237848369L;

    @Column(name = "cart_id")
    Long cartId;

    @Column(name = "product_id")
    Long productId;
    
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CartDetailKey that = (CartDetailKey) o;
//        return that.cart_id.equals(this.cart_id) && that.product_id.equals(this.product_id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(cart_id, product_id);
//    }

	public Long getCart_id() {
		return cartId;
	}

	public void setCart_id(Long cartId) {
		this.cartId = cartId;
	}

	public Long getProduct_id() {
		return productId;
	}

	public void setProduct_id(Long productId) {
		this.productId = productId;
	}
}
