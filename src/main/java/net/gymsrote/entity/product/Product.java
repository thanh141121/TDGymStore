package net.gymsrote.entity.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.MediaResource;
import net.gymsrote.entity.UpdatableAvatar;
import net.gymsrote.entity.EnumEntity.EProductStatus;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product implements UpdatableAvatar{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private ProductCategory category;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<ProductVariation> variations = new ArrayList<>();

	@OneToMany(mappedBy = "product",cascade =  CascadeType.ALL)
	private List<ProductImage> images = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_avatar")
	private MediaResource avatar;
	
	@Column(name = "name")
	private String name;
	
	@Lob
	@Column(name = "description")
	private String description;
	
	@Column(name = "min_price")
	private Long minPrice;
	
	@Column(name = "max_price")
	private Long maxPrice;
	
	@Column(name = "max_discount")
	private Integer maxDiscount;
	
	@Column(name = "nsold")
	private Long nsold;
	
	@Column(name = "nvisit")
	private Long nvisit;
	
	@Column(name = "average_rating")
	private Double averageRating;
	
	/***/
	@Column(name = "rating1")
	private Integer rating1;
	@Column(name = "rating2")
	private Integer rating2;
	@Column(name = "rating3")
	private Integer rating3;
	@Column(name = "rating4")
	private Integer rating4;
	@Column(name = "rating5")
	private Integer rating5;
	/***/
	
	@Column(name = "status", nullable = false)
	private EProductStatus status;
	
	@Column(name = "created_by")
	@CreatedBy
	private String createdBy;

	@Column(name = "created_date")
	@CreatedDate
	private Date createdDate;
	
	@Column(name = "last_modified_by")
	@LastModifiedBy
	private String lastModifiedBy;
	
	@Column(name = "last_modified_date")
	@LastModifiedDate
	private Date lastModifiedDate;
	
	@Column(name = "weight")
	private Integer weight;
	@Column(name = "length")
	private Integer length;
	@Column(name = "width")
	private Integer width;
	@Column(name = "height")
	private Integer height;

	public Product(ProductCategory category, String name, String description, MediaResource avatar, EProductStatus status) {
		this.category = category;
		this.name = name;
		this.description = description;
		this.avatar = avatar;
		this.status = status;

		this.minPrice = null;
		this.maxPrice = null;
		this.nsold = 0L;
		this.nvisit = 0L;
		
		this.averageRating = 0.0;
		this.rating1 = 0;
		this.rating2 = 0;
		this.rating3 = 0;
		this.rating4 = 0;
		this.rating5 = 0;
	}
	
	public Integer getTotalRatingTimes() {
		return rating1 + rating2 + rating3 + rating4 + rating5;
	}
	
	public List<MediaResource> allImgUrl(){
		List<MediaResource> urls = new ArrayList<>();
		urls.add(this.avatar);
		for(ProductImage proImg : this.images) {
			urls.add(proImg.getMedia());
		}
		return urls;
	}
	
	public HashMap<Long, MediaResource> allImgVar(){
		HashMap<Long, MediaResource> result = new HashMap<>();
		for(ProductVariation proVar : this.variations ) {
			result.put(proVar.getId(), proVar.getAvatar());
		}
		return result;
	}
}
