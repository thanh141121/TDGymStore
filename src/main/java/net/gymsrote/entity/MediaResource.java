package net.gymsrote.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.product.ProductImage;
import net.gymsrote.entity.user.User;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media_resource")
public class MediaResource {
	
	public MediaResource(String url, String publicId, String resourceType) {
		this.url = url;
		this.publicId = publicId;
		this.resourceType = resourceType;
	}

	@Override
	public String toString() {
		return url;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@NotBlank
	private String url;
	
	@Column(name = "public_id")
	private String publicId;
	
	@Column(name = "resource_type")
	private String resourceType;

//	@OneToOne(mappedBy = "avatar")
//	private UserEntity user;


}
