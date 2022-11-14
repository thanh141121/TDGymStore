package net.gymsrote.entity.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.EnumEntity.EProductStatus;
import net.gymsrote.entity.cart.CartDetailEntity;
import net.gymsrote.entity.order.OrderDetailEntity;
import net.gymsrote.entity.product.ProductEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_image")
public class ProductImageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	private ProductEntity product;
	
	@OneToOne
	@JoinColumn(name = "id_media")
	private MediaResource media;
	
	public ProductImageEntity(ProductEntity product, MediaResource media) {
		this.product = product;
		this.media = media;
	}

}
