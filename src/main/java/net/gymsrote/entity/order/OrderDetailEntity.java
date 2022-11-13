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
import net.gymsrote.entity.product.ProductEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders_detail")
public class OrderDetailEntity {
	
    @EmbeddedId
    private OrderDetailKey id;
    
    @ManyToOne
    @MapsId("ordersId")
    @JoinColumn(name = "orders_id")
    OrderEntity orders;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    ProductEntity product;
    
    @Column(name="quantity")
    private Long quantity;
	
	

}
