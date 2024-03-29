package net.gymsrote.entity.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.UpdatableAvatar;
import net.gymsrote.entity.EnumEntity.EProductVariationStatus;
import net.gymsrote.entity.comment.Comment;
import net.gymsrote.entity.listener.ProductVariationListener;

@Getter @Setter
@NoArgsConstructor
@Entity
@EntityListeners(ProductVariationListener.class)
@Table(name = "product_variation")
public class ProductVariation implements UpdatableAvatar{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;
	
	@Column(name = "variation_name")
	private String variationName;
	
	@OneToOne
	@JoinColumn(name = "media_resource_id", unique = true)
	private MediaResource avatar;
	
	
	@Column(name = "price", nullable = false)
	private Long price;
	
	@Column(name = "available_quantity")
	private Long availableQuantity;
	
	@Column(name = "discount", nullable = false)
	private Integer discount;
	
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private EProductVariationStatus status;	

	
	@OneToMany(mappedBy = "productVariation", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();
	
	public Long getFinalPrice(){
		if(discount!= null)
			return (Long) (this.price*(100 - this.discount)/100);
		return this.price;
	}
	
	public ProductVariation(Product product, String variationName, Long price, Long availableQuantity,
			EProductVariationStatus status) {
		this.product = product;
		this.variationName = variationName;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.status = status;
	}

	public ProductVariation(Product product, String variationName, Long price, Long availableQuantity, Integer discount, MediaResource avatar,
			EProductVariationStatus status) {
		this(product, variationName, price, availableQuantity, status);
		this.avatar = avatar;
		
		if (discount == null) {
			this.discount = 0;
		}
		else {
			this.discount = discount;
		}
	}
	
	public Long getPriceAfterDiscount() {
		return Long.valueOf(Math.round(price * (1 - discount / 100.0f)));
	}
}
