package net.gymsrote.entity.order;

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
@EqualsAndHashCode
@Embeddable
public class OrderDetailKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 465171354677032464L;

	@Column(name = "order_id")
	Long orderId;

	@Column(name="id_product_variation")
	private Long idProductVariation;
}
