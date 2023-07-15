package net.gymsrote.entity.comment;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.gymsrote.entity.product.Product;
import net.gymsrote.entity.product.ProductVariation;
import net.gymsrote.entity.user.User;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
    
	@ManyToOne
	@JoinColumn(name = "productVariation_id", nullable = false)
	private ProductVariation productVariation;
       
    @Column
    private Long rate;
    
    @Column
    private String description;
    
	@Column(name = "created_date")
	@CreatedDate
	private Date createdDate;

	public Comment(User user, ProductVariation productVariation, Long rate, String description) {
		super();
		this.user = user;
		this.productVariation = productVariation;
		this.rate = rate;
		this.description = description;
	}
    
    
    

}
