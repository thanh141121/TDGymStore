package net.gymsrote.entity.order;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderDetailKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 465171354677032464L;
	
    @Column(name = "orders_id")
    Long ordersId;

    @Column(name = "product_id")
    Long productId;
    

	public Long getOrder_id() {
		return ordersId;
	}

	public void setOrder_id(Long orderId) {
		this.ordersId = orderId;
	}

	public Long getProduct_id() {
		return productId;
	}

	public void setProduct_id(Long productId) {
		this.productId = productId;
	}

}
