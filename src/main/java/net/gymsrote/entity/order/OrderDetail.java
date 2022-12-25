package net.gymsrote.entity.order;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.product.ProductVariation;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {
	
    @EmbeddedId
    private OrderDetailKey id;
    
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    Order order;
    
	@ManyToOne
	@MapsId("idProductVariation")
	@JoinColumn(name = "id_product_variation", insertable = false, updatable = false)
	private ProductVariation productVariation;
    
    @Column(name="quantity")
    private Long quantity;

	@Column(name = "unit_price")
	private Long unitPrice;
	
	@Column(name = "reviewed")
	private Boolean reviewed;
	
	public OrderDetail(Order order, ProductVariation productVariation, Long quantity, Long unitPrice) {
		this.order = order;
		this.productVariation = productVariation;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		
		this.id = new OrderDetailKey(order.getId(), productVariation.getId());
		this.reviewed = false;
	}
	

}
