package net.gymsrote.entity.order;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class OrderDetailKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 465171354677032464L;

	@Column(name = "orders_id")
	Long ordersId;

	@Column(name = "product_id")
	Long productId;
}
