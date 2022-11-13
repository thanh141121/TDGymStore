package net.gymsrote.entity.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.order.OrderDetailKey;

@NoArgsConstructor
@EqualsAndHashCode
@Getter @Setter
@Embeddable
public class ProductImageKey implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 7520262869334175431L;
	
    @Column(name = "product_id")
    Long productId;

    @Column(name = "cloud_resource_id")
    Long cloudResourceId;

}
