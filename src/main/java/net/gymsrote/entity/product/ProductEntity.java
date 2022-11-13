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

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.gymsrote.entity.BaseEntity;
import net.gymsrote.entity.CloudResource;
import net.gymsrote.entity.EnumEntity.EOrderStatus;
import net.gymsrote.entity.EnumEntity.EProductStatus;
import net.gymsrote.entity.cart.CartDetailEntity;
import net.gymsrote.entity.order.OrderDetailEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "name")
	private String name;
		
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Long price;
	
	@Column(name = "quantity")
	private Long quantity;
	
	@OneToOne
	@JoinColumn(name = "cloud_resource_id")
	private CloudResource cloud;
	
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private EProductStatus  status;
	
	
	
	//
	
	@OneToMany(mappedBy = "product")
	private List<OrderDetailEntity> orderDetail = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<CartDetailEntity> cartDetail = new ArrayList<>();
	
	@OneToMany(mappedBy = "product",cascade =  CascadeType.ALL)
	private List<ProductImageEntity> productImage = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private ProductCategoryEntity category;
}
