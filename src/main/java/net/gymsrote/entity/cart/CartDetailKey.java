package net.gymsrote.entity.cart;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class CartDetailKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8283881237848369L;

	@Column(name = "user_id")
	Long userId;

	@Column(name="id_product_variation")
	private Long idProductVariation;
}
