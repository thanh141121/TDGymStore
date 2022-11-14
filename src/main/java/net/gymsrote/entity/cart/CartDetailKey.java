package net.gymsrote.entity.cart;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class CartDetailKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8283881237848369L;

	@Column(name = "cart_id")
	Long cartId;

	@Column(name = "product_id")
	Long productId;
}
